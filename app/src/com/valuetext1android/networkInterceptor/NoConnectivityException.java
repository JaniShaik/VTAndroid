package com.valuetext1android.networkInterceptor;

import java.io.IOException;

/**
 * Created by JANI SHAIK on 14/01/2020
 */

public class NoConnectivityException extends IOException {
    public String getMessage() {
        return "No connectivity exception";
    }
}
