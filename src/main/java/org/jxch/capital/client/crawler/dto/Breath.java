package org.jxch.capital.client.crawler.dto;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.client.uilt.CollU;
import org.jxch.capital.client.uilt.ColorU;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Breath {

    private List<Item> scores = new ArrayList<>();

    public Breath add(LocalDate date, String type, Integer score) {
        scores.add(Item.builder().date(date).type(type).score(score).build());
        return this;
    }

    public Breath retained(int length) {
        Breath breath = new Breath();
        List<LocalDate> localDates = getDateReverse().stream().limit(length).toList();
        List<String> types = getTypes();

        for (LocalDate localDate : localDates) {
            for (String type : types) {
                breath.add(localDate, type, getItem(type, localDate).getScore());
            }
        }

        return breath;
    }

    public List<List<String>> getScoresStrTable() {
        return CollU.append(
                getTypes().stream().map(type -> CollU.append(getItems(type).stream().map(Item::getScore).map(String::valueOf).collect(Collectors.toList()), type)).collect(Collectors.toList()),
                getDates().stream().map(date -> DateUtil.format(DateUtil.date(date), "yy\nMM\ndd")).toList());
    }

    @JSONField(serialize = false)
    public Breath.Item getItem(String type, LocalDate date) {
        return scores.stream().filter(item -> Objects.equals(type, item.getType()) && Objects.equals(date, item.getDate()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个数据：" + type + " - " + date.toString()));
    }

    @JSONField(serialize = false)
    public List<Breath.Item> getItemsReversedDate(String type) {
        return scores.stream().filter(item -> Objects.equals(type, item.getType()))
                .sorted(Comparator.comparing(Item::getDate).reversed())
                .toList();
    }

    @JSONField(serialize = false)
    public List<Breath.Item> getItems(String type) {
        return scores.stream().filter(item -> Objects.equals(type, item.getType()))
                .sorted(Comparator.comparing(Item::getDate))
                .toList();
    }

    @JSONField(serialize = false)
    public List<Breath.Item> getItems(LocalDate date) {
        return scores.stream().filter(item -> Objects.equals(date, item.getDate()))
                .sorted(Comparator.comparing(Item::getType))
                .toList();
    }

    @JSONField(serialize = false)
    public List<String> getTypes() {
        return scores.stream().map(Item::getType).distinct().sorted().toList();
    }

    @JSONField(serialize = false)
    public List<LocalDate> getDateReverse() {
        return scores.stream().map(Item::getDate).distinct().sorted(Comparator.reverseOrder()).toList();
    }

    @JSONField(serialize = false)
    public List<LocalDate> getDates() {
        return scores.stream().map(Item::getDate).distinct().sorted().toList();
    }

    @JSONField(serialize = false)
    public String htmlTable() {
        return Breath.htmlTable(this);
    }

    @JSONField(serialize = false)
    public String htmlTable(Integer length) {
        return Breath.htmlTable(retained(length));
    }

    @JSONField(serialize = false)
    public static String htmlTable(Breath breath) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<table>");
        for (String type : breath.getTypes()) {
            htmlBuilder.append("<tr>");

            for (Breath.Item item : breath.getItems(type)) {
                Color color = ColorU.getColorFromGradientByRTG(item.getScore() - 50, 50);
                htmlBuilder.append("<td style=\"background-color: ").append(ColorU.colorTo16(color)).append("; text-align: center;\">");
                htmlBuilder.append(item.getScore());
                htmlBuilder.append("</td>");
            }

            htmlBuilder.append("<td>").append(type).append("</td>").append("</tr>");
        }

        htmlBuilder.append("<tr>");
        for (LocalDate date : breath.getDates()) {
            String dateStr = DateUtil.format(DateUtil.date(date), "yy\nMM\ndd");
            htmlBuilder.append("<td style=\"text-align: center;\">").append(dateStr).append("</td>");
        }
        htmlBuilder.append("</tr>");
        htmlBuilder.append("</table></br>");

        return htmlBuilder.toString();
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Item {
        private LocalDate date;
        private String type;
        private Integer score;
    }

}
