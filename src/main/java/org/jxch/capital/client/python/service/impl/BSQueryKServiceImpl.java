package org.jxch.capital.client.python.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.client.config.AppConfig;
import org.jxch.capital.client.python.binder.BSQueryK2CSV;
import org.jxch.capital.client.python.service.BSQueryKService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BSQueryKServiceImpl implements BSQueryKService {
    private final BSQueryK2CSV bsQueryK2CSV;
    private final AppConfig appConfig;




}
