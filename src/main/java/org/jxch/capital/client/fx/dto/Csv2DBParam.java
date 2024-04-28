package org.jxch.capital.client.fx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.fx.template.ParamSuggestive;
import org.jxch.capital.client.khash.rocks.Files2RocksDB;
import org.jxch.capital.client.service.NamedOrderedServices;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Csv2DBParam {
    @Builder.Default
    private String csvPath = "G:\\app\\backup\\data\\stock_data\\csv\\5-2";
    private String files2DbService;

    @ParamSuggestive("files2DbService")
    private List<String> allFiles2DbServices = NamedOrderedServices.allSortedServiceNames(Files2RocksDB.class);
}
