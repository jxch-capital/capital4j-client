package org.jxch.capital.client.stock.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockFrequency {
    YEAR_1("1y"),
    MONTH_1("1MO"),
    MONTH_3("3MO"),
    WEEK_1("1w"),
    DAY_1("1d"),
    DAY_5("5d"),
    MINUTE_1("1m"),
    MINUTE_5("5m"),
    MINUTE_10("10m"),
    MINUTE_15("15m"),
    MINUTE_30("30m"),
    MINUTE_60("60m"),
    HOUR_1("1h"),
    HOUR_4("4h"),
    ;
    private final String value;
}
