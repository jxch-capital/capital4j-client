package org.jxch.capital.client.python.register;

@FunctionalInterface
public interface PyBindRunner<T> {

    String run(T command);

}
