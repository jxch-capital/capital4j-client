package org.jxch.capital.client.crawler.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DatavizGraph {
    @JSONField(name = "fear_and_greed")
    private FearAndGreed fearAndGreed;
    @JSONField(name = "fear_and_greed_historical")
    private Historical fearAndGreedHistorical;
    @JSONField(name = "market_momentum_sp500")
    private Historical marketMomentumSp500;
    @JSONField(name = "market_momentum_sp125")
    private Historical marketMomentumSp125;
    @JSONField(name = "stock_price_strength")
    private Historical stockPriceStrength;
    @JSONField(name = "stock_price_breadth")
    private Historical stockPriceBreadth;
    @JSONField(name = "put_call_options")
    private Historical putCallOptions;
    @JSONField(name = "market_volatility_vix")
    private Historical marketVolatilityVix;
    @JSONField(name = "market_volatility_vix_50")
    private Historical marketVolatilityVix50;
    @JSONField(name = "junk_bond_demand")
    private Historical junkBondDemand;
    @JSONField(name = "safe_haven_demand")
    private Historical safeHavenDemand;

    @Data
    public static class FearAndGreed {
        private Double score;
        private String rating;
        private Date timestamp;
        @JSONField(name = "previous_close")
        private Double previousClose;
        @JSONField(name = "previous_1_week")
        private Double previous1Week;
        @JSONField(name = "previous_1_month")
        private Double previous1Month;
        @JSONField(name = "previous_1_year")
        private Double previous1Year;
    }

    @Data
    public static class Historical {
        private Date timestamp;
        private Double score;
        private String rating;
        private List<XYData> data;
    }

    @Data
    public static class XYData {
        private Date x;
        private Double y;
        private String rating;
    }

}
