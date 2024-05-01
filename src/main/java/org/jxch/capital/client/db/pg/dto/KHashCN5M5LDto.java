package org.jxch.capital.client.db.pg.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KHashCN5M5LDto {
    private Long id;

    private Integer hash5;
    private Long hash10;
    private Long hash16;
    private Long hash24_1;
    private Long hash24_2;
    private Long hash48_1;
    private Long hash48_2;
    private Long hash48_3;
    private Long hash48_4;
    private String ex;

    private Date date;
    private String codeNumberString;

    public String getCode() {
        return ex + "." + this.codeNumberString;
    }

    public KHashCN5M5LDto setCode(@NotNull String code) {
        String[] codeSplit = code.split("\\.");
        this.ex = codeSplit[0];
        this.codeNumberString = codeSplit[1];
        return this;
    }

    public String getHash48String() {
        return String.valueOf(hash48_1) + hash48_2 + hash48_3 + hash48_4;
    }

    public String getHash24String() {
        return String.valueOf(hash24_1) + hash24_2;
    }

    public String decodeAndGetCodeNumberString() {
        this.codeNumberString = String.valueOf(id).substring(5, 11);
        return this.codeNumberString;
    }

    public Date decodeAndGetDate() {
        this.date = DateUtil.parse(String.valueOf(id).substring(11), "yyyyMMdd");
        return this.date;
    }

    public Long encodAndGetId() {
        this.id = Long.parseLong(String.valueOf(hash48_1).substring(0, 5) + codeNumberString + DateUtil.format(date, "yyyyMMdd"));
        return this.id;
    }

    public KHashCN5M5LDto decodeId() {
        decodeAndGetCodeNumberString();
        decodeAndGetDate();
        return this;
    }

    public KHashCN5M5LDto encodId() {
        encodAndGetId();
        return this;
    }

}
