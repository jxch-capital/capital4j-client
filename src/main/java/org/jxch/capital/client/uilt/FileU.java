package org.jxch.capital.client.uilt;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.jxch.capital.client.config.AppConfig;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
public class FileU {

    @NonNull
    public static File tmpFile() {
        return new File(SpringUtil.getBean(AppConfig.class).getTmpPath());
    }

    @NonNull
    public static File tmpFile(String fileName) {
        return tmpFile().toPath().resolve(fileName).toFile();
    }

    @NonNull
    public static String tmpFilePath(String fileName) {
        return tmpFile().toPath().resolve(fileName).toFile().getAbsolutePath();
    }

    public static File writeString2tmpFile(String text, String suffix) {
        return FileUtil.writeString(text, tmpFile(UUID.randomUUID() + suffix), Charset.defaultCharset());
    }

    @NonNull
    @SneakyThrows
    public static File downloadFile2tmp(@NonNull String url) {
        File tmpFile = tmpFile(url.substring(url.lastIndexOf("/") + 1));
        URL pipUrl = new URL(url);
        try (InputStream in = pipUrl.openStream()) {
            Files.copy(in, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return tmpFile;
    }

    @NonNull
    @SneakyThrows
    public static File unTarGz2tmp(@NonNull File tarGz) {
        File tmpFile = tmpFile(tarGz.getName().replaceAll("\\.tar\\.gz", ""));
        FileUtil.mkdir(tmpFile);

        try (InputStream inputStream = Files.newInputStream(tarGz.toPath());
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             GzipCompressorInputStream gzipInputStream = new GzipCompressorInputStream(bufferedInputStream);
             TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(gzipInputStream)) {

            org.apache.commons.compress.archivers.ArchiveEntry entry;
            while ((entry = tarArchiveInputStream.getNextEntry()) != null) {
                if (!tarArchiveInputStream.canReadEntryData(entry)) {
                    continue;
                }
                String entryName = entry.getName();
                File entryFile = new File(tmpFile, entryName);
                if (entry.isDirectory()) {
                    if (!entryFile.isDirectory() && !entryFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + entryFile);
                    }
                } else {
                    File parent = entryFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    try (OutputStream outputStream = Files.newOutputStream(entryFile.toPath())) {
                        IOUtils.copy(tarArchiveInputStream, outputStream);
                    }
                }
            }
        }

        return tmpFile;
    }


}
