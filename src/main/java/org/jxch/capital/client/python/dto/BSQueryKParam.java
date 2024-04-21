package org.jxch.capital.client.python.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.uilt.FileU;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BSQueryKParam {
    @Builder.Default
    private String csvFile = FileU.tmpFilePath(UUID.randomUUID() + ".csv");
    private String code;
    private String fields;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate = DateUtil.date();
    private String frequency;
    @Builder.Default
    private String adjustflag = "2";

    public static BSQueryKParam dayParam() {
        return BSQueryKParam.builder()
                .frequency("d")
                .fields("date,code,open,high,low,close,volume")
                .build();
    }

    public static BSQueryKParam dailyParam() {
        return BSQueryKParam.builder()
                .frequency("5")
                .fields("time,code,open,high,low,close,volume")
                .build();
    }

    public BSQueryKParam setDayFields() {
        setFields("date,code,open,high,low,close,volume");
        return this;
    }

    public BSQueryKParam setDailyFields() {
        setFields("time,code,open,high,low,close,volume");
        return this;
    }

}
