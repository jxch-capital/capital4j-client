package org.jxch.capital.client.exception;

public class OperationalException extends RuntimeException {

    public OperationalException() {
    }

    public OperationalException(String message) {
        super(message);
    }

    public OperationalException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationalException(Throwable cause) {
        super(cause);
    }

    public OperationalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
