package org.jxch.capital.client.db.po;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jxch.capital.client.db.sharding.k_line_his_cn.KLineHisCNSharding;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "k_line_his_cn")
@Table(indexes = {@Index(name = "k_line_his_cn_code_date", columnList = "code, date")})
public class KLineHisCN extends KLineHisCNSharding {

}
