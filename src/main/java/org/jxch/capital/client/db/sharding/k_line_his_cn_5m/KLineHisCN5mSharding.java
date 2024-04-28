package org.jxch.capital.client.db.sharding.k_line_his_cn_5m;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jxch.capital.client.db.po.BasePo;

import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class KLineHisCN5mSharding extends BasePo {
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

    @Entity(name = "k_line_his_cn_5m_1999")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_1999_code_date", columnList = "code, date")})
    public static class KLineHisCN5m1999 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2000")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2000_code_date", columnList = "code, date")})
    public static class KLineHisCN5m2000 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2001")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2001_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2001 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2002")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2002_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2002 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2003")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2003_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2003 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2004")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2004_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2004 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2005")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2005_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2005 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2006")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2006_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2006 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2007")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2007_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2007 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2008")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2008_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2008 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2009")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2009_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2009 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2010")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2010_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2010 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2011")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2011_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2011 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2012")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2012_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2012 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2013")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2013_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2013 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2014")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2014_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2014 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2015")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2015_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2015 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2016")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2016_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2016 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2017")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2017_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2017 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2018")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2018_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2018 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2019")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2019_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2019 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2020")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2020_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2020 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2021")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2021_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2021 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2022")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2022_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2022 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2023")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2023_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2023 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2024")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2024_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2024 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2025")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2025_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2025 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2026")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2026_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2026 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2027")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2027_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2027 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2028")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2028_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2028 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2029")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2029_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2029 extends KLineHisCN5mSharding {
    }

    @Entity(name = "k_line_his_cn_5m_2030")
    @Table(indexes = {@Index(name = "k_line_his_cn_5m_2030_code_date", columnList = "code, date")})
    public static final class KLineHisCN5m2030 extends KLineHisCN5mSharding {
    }
}
