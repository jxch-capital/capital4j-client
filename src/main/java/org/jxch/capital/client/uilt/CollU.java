package org.jxch.capital.client.uilt;

import lombok.NonNull;

import java.util.List;

public class CollU {

    public static <T> List<T> append(@NonNull List<T> list, T item) {
        list.add(item);
        return list;
    }

}
