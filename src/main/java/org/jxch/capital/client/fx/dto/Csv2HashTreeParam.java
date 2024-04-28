package org.jxch.capital.client.fx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.fx.template.ParamSuggestive;
import org.jxch.capital.client.khash.tree.Files2HashTree;
import org.jxch.capital.client.service.NamedOrderedServices;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Csv2HashTreeParam {
    @Builder.Default
    private String csvPath = "G:\\app\\backup\\data\\stock_data\\csv\\5-2";
    private String files2HashTreeService;

    @ParamSuggestive("files2HashTreeService")
    private List<String> allFiles2HashTreeServices = NamedOrderedServices.allSortedServiceNames(Files2HashTree.class);
}
