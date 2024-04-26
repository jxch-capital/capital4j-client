package org.jxch.capital.client.db.sharding.k_line_his_cn;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jxch.capital.client.db.po.BasePo;

import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class KLineHisCNSharding extends BasePo {
    @Column(nullable = false)
    private Integer code;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, length = 2)
    private String ex;

    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double volume;

    @Entity(name = "k_line_his_cn_1999")
    @Table(indexes = {@Index(name = "k_line_his_cn_1999_code", columnList = "code"), @Index(name = "k_line_his_cn_1999_date", columnList = "date"),})
    public static class KLineHisCN1999 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2000")
    @Table(indexes = {@Index(name = "k_line_his_cn_2000_code", columnList = "code"), @Index(name = "k_line_his_cn_2000_date", columnList = "date"),})
    public static class KLineHisCN2000 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2001")
    @Table(indexes = {@Index(name = "k_line_his_cn_2001_code", columnList = "code"), @Index(name = "k_line_his_cn_2001_date", columnList = "date"),})
    public static final class KLineHisCN2001 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2002")
    @Table(indexes = {@Index(name = "k_line_his_cn_2002_code", columnList = "code"), @Index(name = "k_line_his_cn_2002_date", columnList = "date"),})
    public static final class KLineHisCN2002 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2003")
    @Table(indexes = {@Index(name = "k_line_his_cn_2003_code", columnList = "code"), @Index(name = "k_line_his_cn_2003_date", columnList = "date"),})
    public static final class KLineHisCN2003 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2004")
    @Table(indexes = {@Index(name = "k_line_his_cn_2004_code", columnList = "code"), @Index(name = "k_line_his_cn_2004_date", columnList = "date"),})
    public static final class KLineHisCN2004 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2005")
    @Table(indexes = {@Index(name = "k_line_his_cn_2005_code", columnList = "code"), @Index(name = "k_line_his_cn_2005_date", columnList = "date"),})
    public static final class KLineHisCN2005 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2006")
    @Table(indexes = {@Index(name = "k_line_his_cn_2006_code", columnList = "code"), @Index(name = "k_line_his_cn_2006_date", columnList = "date"),})
    public static final class KLineHisCN2006 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2007")
    @Table(indexes = {@Index(name = "k_line_his_cn_2007_code", columnList = "code"), @Index(name = "k_line_his_cn_2007_date", columnList = "date"),})
    public static final class KLineHisCN2007 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2008")
    @Table(indexes = {@Index(name = "k_line_his_cn_2008_code", columnList = "code"), @Index(name = "k_line_his_cn_2008_date", columnList = "date"),})
    public static final class KLineHisCN2008 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2009")
    @Table(indexes = {@Index(name = "k_line_his_cn_2009_code", columnList = "code"), @Index(name = "k_line_his_cn_2009_date", columnList = "date"),})
    public static final class KLineHisCN2009 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2010")
    @Table(indexes = {@Index(name = "k_line_his_cn_2010_code", columnList = "code"), @Index(name = "k_line_his_cn_2010_date", columnList = "date"),})
    public static final class KLineHisCN2010 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2011")
    @Table(indexes = {@Index(name = "k_line_his_cn_2011_code", columnList = "code"), @Index(name = "k_line_his_cn_2011_date", columnList = "date"),})
    public static final class KLineHisCN2011 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2012")
    @Table(indexes = {@Index(name = "k_line_his_cn_2012_code", columnList = "code"), @Index(name = "k_line_his_cn_2012_date", columnList = "date"),})
    public static final class KLineHisCN2012 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2013")
    @Table(indexes = {@Index(name = "k_line_his_cn_2013_code", columnList = "code"), @Index(name = "k_line_his_cn_2013_date", columnList = "date"),})
    public static final class KLineHisCN2013 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2014")
    @Table(indexes = {@Index(name = "k_line_his_cn_2014_code", columnList = "code"), @Index(name = "k_line_his_cn_2014_date", columnList = "date"),})
    public static final class KLineHisCN2014 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2015")
    @Table(indexes = {@Index(name = "k_line_his_cn_2015_code", columnList = "code"), @Index(name = "k_line_his_cn_2015_date", columnList = "date"),})
    public static final class KLineHisCN2015 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2016")
    @Table(indexes = {@Index(name = "k_line_his_cn_2016_code", columnList = "code"), @Index(name = "k_line_his_cn_2016_date", columnList = "date"),})
    public static final class KLineHisCN2016 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2017")
    @Table(indexes = {@Index(name = "k_line_his_cn_2017_code", columnList = "code"), @Index(name = "k_line_his_cn_2017_date", columnList = "date"),})
    public static final class KLineHisCN2017 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2018")
    @Table(indexes = {@Index(name = "k_line_his_cn_2018_code", columnList = "code"), @Index(name = "k_line_his_cn_2018_date", columnList = "date"),})
    public static final class KLineHisCN2018 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2019")
    @Table(indexes = {@Index(name = "k_line_his_cn_2019_code", columnList = "code"), @Index(name = "k_line_his_cn_2019_date", columnList = "date"),})
    public static final class KLineHisCN2019 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2020")
    @Table(indexes = {@Index(name = "k_line_his_cn_2020_code", columnList = "code"), @Index(name = "k_line_his_cn_2020_date", columnList = "date"),})
    public static final class KLineHisCN2020 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2021")
    @Table(indexes = {@Index(name = "k_line_his_cn_2021_code", columnList = "code"), @Index(name = "k_line_his_cn_2021_date", columnList = "date"),})
    public static final class KLineHisCN2021 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2022")
    @Table(indexes = {@Index(name = "k_line_his_cn_2022_code", columnList = "code"), @Index(name = "k_line_his_cn_2022_date", columnList = "date"),})
    public static final class KLineHisCN2022 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2023")
    @Table(indexes = {@Index(name = "k_line_his_cn_2023_code", columnList = "code"), @Index(name = "k_line_his_cn_2023_date", columnList = "date"),})
    public static final class KLineHisCN2023 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2024")
    @Table(indexes = {@Index(name = "k_line_his_cn_2024_code", columnList = "code"), @Index(name = "k_line_his_cn_2024_date", columnList = "date"),})
    public static final class KLineHisCN2024 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2025")
    @Table(indexes = {@Index(name = "k_line_his_cn_2025_code", columnList = "code"), @Index(name = "k_line_his_cn_2025_date", columnList = "date"),})
    public static final class KLineHisCN2025 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2026")
    @Table(indexes = {@Index(name = "k_line_his_cn_2026_code", columnList = "code"), @Index(name = "k_line_his_cn_2026_date", columnList = "date"),})
    public static final class KLineHisCN2026 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2027")
    @Table(indexes = {@Index(name = "k_line_his_cn_2027_code", columnList = "code"), @Index(name = "k_line_his_cn_2027_date", columnList = "date"),})
    public static final class KLineHisCN2027 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2028")
    @Table(indexes = {@Index(name = "k_line_his_cn_2028_code", columnList = "code"), @Index(name = "k_line_his_cn_2028_date", columnList = "date"),})
    public static final class KLineHisCN2028 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2029")
    @Table(indexes = {@Index(name = "k_line_his_cn_2029_code", columnList = "code"), @Index(name = "k_line_his_cn_2029_date", columnList = "date"),})
    public static final class KLineHisCN2029 extends KLineHisCNSharding {
    }

    @Entity(name = "k_line_his_cn_2030")
    @Table(indexes = {@Index(name = "k_line_his_cn_2030_code", columnList = "code"), @Index(name = "k_line_his_cn_2030_date", columnList = "date"),})
    public static final class KLineHisCN2030 extends KLineHisCNSharding {
    }
}
