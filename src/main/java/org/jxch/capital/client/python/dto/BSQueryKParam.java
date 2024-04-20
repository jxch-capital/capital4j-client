package org.jxch.capital.client.python.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BSQueryKParam {
    private String csvFile;
    private String code;
    private String fields;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String frequency;
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

}
