package org.jxch.capital.client.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.client.db.clickhouse.dao.KHashCN5M5LRepository;
import org.jxch.capital.client.db.clickhouse.dto.KHashCN5M5LDto;
import org.jxch.capital.client.db.clickhouse.mapper.KHashCN5M5LMapper;
import org.jxch.capital.client.service.KHashCN5M5LService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KHashCN5M5LServiceImpl implements KHashCN5M5LService {
    private final KHashCN5M5LRepository kHashCN5M5LRepository;
    private final KHashCN5M5LMapper kHashCN5M5LMapper;

    @Override
    public Integer saveAll(List<KHashCN5M5LDto> dtoList) {
        return kHashCN5M5LRepository.insert(kHashCN5M5LMapper.toKHashCN5M5L(dtoList));
    }

    @NotNull
    private String hashFill(@NotNull String hashPrefix, int num) {
        return hashPrefix + "0".repeat(num - hashPrefix.length());
    }

    @NotNull
    private String hashIncFill(@NotNull String hashPrefix, int num) {
        return hashFill(new BigDecimal(hashPrefix).add(new BigDecimal("1")).toString(), num);
    }

    @Override
    public List<KHashCN5M5LDto> findByHash5Prefix(@NotNull String hashPrefix) {
        Integer form = Integer.parseInt(hashFill(hashPrefix, 5));
        Integer to = Integer.parseInt(hashIncFill(hashPrefix, 5));
        return kHashCN5M5LMapper.toKHashCN5M5LDto(kHashCN5M5LRepository.findByHash5Between(form, to));
    }

    @Override
    public List<KHashCN5M5LDto> findByHash10Prefix(String hashPrefix) {
        Long form = Long.parseLong(hashFill(hashPrefix, 10));
        Long to = Long.parseLong(hashIncFill(hashPrefix, 10));
        return kHashCN5M5LMapper.toKHashCN5M5LDto(kHashCN5M5LRepository.findByHash10Between(form, to));
    }

    @Override
    public List<KHashCN5M5LDto> findByHash16Prefix(String hashPrefix) {
        Long form = Long.parseLong(hashFill(hashPrefix, 16));
        Long to = Long.parseLong(hashIncFill(hashPrefix, 16));
        return kHashCN5M5LMapper.toKHashCN5M5LDto(kHashCN5M5LRepository.findByHash16Between(form, to));
    }

    @Override
    public List<KHashCN5M5LDto> findByHash24Prefix(String hashPrefix) {
        BigDecimal form = new BigDecimal(hashFill(hashPrefix, 24));
        BigDecimal to = new BigDecimal(hashIncFill(hashPrefix, 24));
        return kHashCN5M5LMapper.toKHashCN5M5LDto(kHashCN5M5LRepository.findByHash24Between(form, to));
    }

    @Override
    public List<KHashCN5M5LDto> findByHash48Prefix(String hashPrefix) {
        BigDecimal form = new BigDecimal(hashFill(hashPrefix, 48));
        BigDecimal to = new BigDecimal(hashIncFill(hashPrefix, 48));
        return kHashCN5M5LMapper.toKHashCN5M5LDto(kHashCN5M5LRepository.findByHash48Between(form, to));
    }

    @PostConstruct
    public void onApplicationEvent() {
        kHashCN5M5LRepository.createTableIfNotExist();
    }

}
