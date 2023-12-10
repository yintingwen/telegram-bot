package com.dething.cloud.common.core.util;

import java.util.function.Function;

public class EnumUtil {
    public static <T extends Enum<T>, V> T fromPropertyValue(Class<T> enumClass, Function<T, V> propertyAccessor, V value) {
        for (T constant : enumClass.getEnumConstants()) {
            if (propertyAccessor.apply(constant).equals(value)) {
                return constant;
            }
        }
        throw new IllegalArgumentException("No matching constant for " + value);
    }

    /**
     * 根据值判断是否包含在枚举变量中
     */
    public static <T extends Enum<T>, V> boolean containsPropertyValue(Class<T> enumClass, Function<T, V> propertyAccessor, V value) {
        for (T constant : enumClass.getEnumConstants()) {
            if (propertyAccessor.apply(constant).equals(value)) {
                return true;
            }
        }
        return false;
    }
}
