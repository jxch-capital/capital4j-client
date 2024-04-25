package org.jxch.capital.client.db.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KLineHisCNDto {
    private Integer code;
    private String ex;
    private Date date;

    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;

    private Long id;
    private Long version;
    private Date createTime;
    private Date updateTime;

    public KLineHisCNDto setCodeAndEx(@NonNull String codeEx) {
        String[] split = codeEx.split("\\.");
        this.code = Integer.parseInt(split[0]);
        this.ex = split[1];
        return this;
    }

}
