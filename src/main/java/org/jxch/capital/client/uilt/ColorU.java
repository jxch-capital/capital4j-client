package org.jxch.capital.client.uilt;

import lombok.NonNull;

import java.awt.*;

public class ColorU {

    public static Color getColorFromGradientByRTG(double value, double range) {
        // 设置值的范围
        double min = -range;
        double max = range;

        // 确保值在给定的范围内
        value = Math.min(max, Math.max(min, value));
        double ratio = normalizeValue(value, min, max);

        // 计算红和绿的归一化值
        int red = (int) (120 * (1 - ratio));
        int green = (int) (120 * ratio);
        int blue = 30; // 红色到绿色的过渡中蓝色始终为0

        return new Color(red, green, blue);
    }

    public static double normalizeValue(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static String colorTo16(@NonNull Color color) {
        String R = Integer.toHexString(color.getRed());
        R = R.length() < 2 ? ('0' + R) : R;

        String G = Integer.toHexString(color.getGreen());
        G = G.length() < 2 ? ('0' + G) : G;

        String B = Integer.toHexString(color.getBlue());
        B = B.length() < 2 ? ('0' + B) : B;

        return '#' + R + G + B;
    }

}
