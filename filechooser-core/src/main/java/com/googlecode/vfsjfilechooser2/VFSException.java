package com.googlecode.vfsjfilechooser2;

import java.io.IOException;

/**
 *
 * @author shevek
 */
public class VFSException extends IOException {

    public VFSException() {
    }

    public VFSException(String message) {
        super(message);
    }

    public VFSException(String message, Throwable cause) {
        super(message, cause);
    }

    public VFSException(Throwable cause) {
        super(cause);
    }

}
