package org.jxch.capital.client.db.pg.sharding.po;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.jetbrains.annotations.NotNull;

@Data
@MappedSuperclass
public class KHashCN5M5L {
    public static final Integer SHARDING_NUMBER = 10;
    public static final Integer LEN_ID = 19;
    public static final Integer LEN_HASH5 = 5;
    public static final Integer LEN_HASH10 = 10;
    public static final Integer LEN_HASH16 = 16;
    public static final Integer LEN_HASH24_1 = 12;
    public static final Integer LEN_HASH24_2 = 12;
    public static final Integer LEN_HASH48_1 = 12;
    public static final Integer LEN_HASH48_2 = 12;
    public static final Integer LEN_HASH48_3 = 12;
    public static final Integer LEN_HASH48_4 = 12;

    @NotNull
    public static String getTableNumber(Long id) {
        String idString = String.valueOf(id);
        String idSuffix = idString.substring(0, 5);
        int idSum = idSuffix.chars().reduce(Integer::sum).orElseThrow(() -> new RuntimeException("id编码失败，请检查id是否大于5位"));
        long number = idSum % SHARDING_NUMBER + 1;
        return "0".repeat(2 - String.valueOf(number).length()) + number;
    }

    @NotNull
    public static Long getFormIndexByPrefix(@NotNull String prefix, Integer len) {
        return Long.parseLong(prefix + "0".repeat(len - prefix.length()));
    }

    @NotNull
    public static Long getToIndexByPrefix(@NotNull String prefix, Integer len) {
        return Long.parseLong((1 + Long.parseLong(prefix)) + "0".repeat(len - prefix.length()));
    }

    @Id
    @Comment("prefix5_hash48_1 + codeNumber + yyyyMMdd")
    private Long id;
    private Integer hash5;
    private Long hash10;
    private Long hash16;
    private Long hash24_1;
    private Long hash24_2;
    private Long hash48_1;
    private Long hash48_2;
    private Long hash48_3;
    private Long hash48_4;
    private String ex;

    @Entity(name = "k_hash_cn_5m_5l_01")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_01", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_01", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_01", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_01", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_01", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L01 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_02")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_02", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_02", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_02", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_02", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_02", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L02 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_03")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_03", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_03", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_03", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_03", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_03", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L03 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_04")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_04", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_04", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_04", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_04", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_04", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L04 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_05")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_05", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_05", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_05", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_05", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_05", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L05 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_06")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_06", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_06", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_06", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_06", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_06", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L06 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_07")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_07", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_07", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_07", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_07", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_07", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L07 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_08")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_08", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_08", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_08", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_08", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_08", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L08 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_09")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_09", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_09", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_09", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_09", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_09", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L09 extends KHashCN5M5L {
    }

    @Entity(name = "k_hash_cn_5m_5l_10")
    @Table(indexes = {
            @Index(name = "k_hash_cn_5m_5l_hash5_10", columnList = "hash5"),
            @Index(name = "k_hash_cn_5m_5l_hash10_10", columnList = "hash10"),
            @Index(name = "k_hash_cn_5m_5l_hash16_10", columnList = "hash16"),
            @Index(name = "k_hash_cn_5m_5l_hash24_10", columnList = "hash24_1, hash24_2"),
            @Index(name = "k_hash_cn_5m_5l_hash48_10", columnList = "hash48_1, hash48_2, hash48_3, hash48_4"),
    })
    public static class KHashCN5M5L10 extends KHashCN5M5L {
    }

}
