package org.jxch.capital.client.db.po.sharding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YearBasedShardingAlgorithm
//        implements StandardShardingAlgorithm<Date>
{

//    @Override
//    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
//        Date value = shardingValue.getValue();
//        LocalDateTime localDateTime = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//        // 获取Year
//        int year = localDateTime.getYear();
//        // 目标表名后缀格式化
//        String suffix = String.valueOf(year);
//        for (String tableName : availableTargetNames) {
//            if (tableName.endsWith(suffix)) {
//                return tableName;
//            }
//        }
//        throw new UnsupportedOperationException("Unable to route to table for value: " + value);
//    }
//
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
//        Collection<String> result = new LinkedHashSet<>();
//        // 获取时间范围
//        LocalDateTime lowerDate = rangeShardingValue.getValueRange().hasLowerBound() ?
//                rangeShardingValue.getValueRange().lowerEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
//        LocalDateTime upperDate = rangeShardingValue.getValueRange().hasUpperBound() ?
//                rangeShardingValue.getValueRange().upperEndpoint().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null;
//
//        // 遍历所有的表名，找到符合时间范围的表进行返回
//        for (String tableName : availableTargetNames) {
//            // 假设表名格式 "k_line_his_cn_YYYY"
//            String yearSuffix = tableName.substring(tableName.lastIndexOf('_') + 1);
//            int year = Integer.parseInt(yearSuffix);
//
//            // 如果时间区间的上下界都不存在（也就是没有限制）
//            if (lowerDate == null && upperDate == null) {
//                result.add(tableName);
//                continue;
//            }
//
//            // 如果(year >= lowerYear && year <= upperYear)，则该表名符合条件
//            if ((lowerDate == null || year >= lowerDate.getYear()) && (upperDate == null || year <= upperDate.getYear())) {
//                result.add(tableName);
//            }
//        }
//
//        return result;
//    }
//
//
//
//    @Override
//    public String getType() {
//        return "YEAR_BASED";
//    }

}
