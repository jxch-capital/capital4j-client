package org.jxch.capital.client.service;

import org.springframework.core.Ordered;

public interface NamedOrderedService extends Ordered {

    @Override
    default int getOrder() {
        return 0;
    }

    default String getName() {
        return getClass().getSimpleName();
    }

}
