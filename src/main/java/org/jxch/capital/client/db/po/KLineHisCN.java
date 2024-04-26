package org.jxch.capital.client.db.po;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;

@Data
@Entity(name = "k_line_his_cn")
@EqualsAndHashCode(callSuper = true)
public class KLineHisCN extends KLineHisCNSharding {

}
