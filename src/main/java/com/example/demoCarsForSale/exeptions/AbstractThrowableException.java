package com.example.demoCarsForSale.exeptions;

import org.apache.log4j.Logger;

public abstract class AbstractThrowableException extends RuntimeException {
    private static final Logger LOG = Logger.getLogger(AbstractThrowableException.class);
    protected final int errorStatus;

    protected AbstractThrowableException(String message, Throwable e, int errorStatus) {
        super(message, e);
        this.errorStatus = errorStatus;
        LOG.error(e + " " + message);
    }

    protected AbstractThrowableException(String message, int errorStatus) {
        super(message);
        this.errorStatus = errorStatus;
        LOG.error(message);

    }

    public int getErrorStatus() {
        return errorStatus;
    }
}
