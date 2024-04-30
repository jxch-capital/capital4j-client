package org.jxch.capital.client.db.pg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParentTemplateConfigDto {
    private String configName;
    private String templateName;
    private String templateParam;
    private String scriptParam;

    private Long id;
    private Long version;
    private Date createTime;
    private Date updateTime;
}
