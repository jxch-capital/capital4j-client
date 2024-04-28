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
public class Csv2HashTree2Neo4JParam {
    @Builder.Default
    private String csvPath = "G:\\app\\backup\\data\\stock_data\\csv\\5-2";

    private String files2HashTree2Neo4JService;
    @ParamSuggestive("files2HashTreeService")
    private List<String> allFiles2Hash2Neo4JTreeServices = NamedOrderedServices.allSortedServiceNames(Files2HashTree.class);
}
