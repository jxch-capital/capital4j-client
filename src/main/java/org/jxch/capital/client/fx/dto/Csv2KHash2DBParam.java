package org.jxch.capital.client.fx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.fx.template.ParamSuggestive;
import org.jxch.capital.client.khash.hash2db.Files2Hash2DB;
import org.jxch.capital.client.service.NamedOrderedServices;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Csv2KHash2DBParam {
    @Builder.Default
    private String csvPath = "G:\\app\\backup\\data\\stock_data\\csv\\5-2";
    private List<String> fileNames;
    private String files2Hash2DbService;

    @ParamSuggestive("files2DbService")
    private List<String> allFiles2Hash2DbServices = NamedOrderedServices.allSortedServiceNames(Files2Hash2DB.class);
}
