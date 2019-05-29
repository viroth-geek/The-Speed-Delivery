package com.iota.eshopping.util;

import org.json.JSONObject;

import java.net.SocketException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @author yeakleang.ay on 8/13/18.
 */

public class ExceptionUtils {

    /**
     * get error message
     * @param responseBody ResponseBody
     * @return String
     */
    private static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * translate exception message
     * @param exception Exception
     * @return String
     */
    public static String translateExceptionMessage(Throwable exception) {
        if (exception instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) exception).response().errorBody();
            return getErrorMessage(responseBody);
        } else if (exception instanceof SocketException) {
            SocketException socketException = ((SocketException) exception);
            return socketException.getMessage();
        }
        return exception.getMessage();
    }

}
