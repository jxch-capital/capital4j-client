package org.jxch.capital.client.python.register;

import java.util.List;

public interface PyBindRunnerParamProcessor {


    <T> List<String> encode(T param);


}
