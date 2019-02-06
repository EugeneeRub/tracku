package com.erproject.busgo.utils;

import com.erproject.busgo.data.data.responses.SignUpResponseError;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorConverter {

    public static SignUpResponseError getErrorAuth(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            try {
                Gson gson = new Gson();
                TypeAdapter<SignUpResponseError> adapter = gson.getAdapter(SignUpResponseError.class);

                return adapter.fromJson(Objects.requireNonNull(responseBody).string());
            } catch (IOException e1) {
                return null;
            } catch (NullPointerException e1) {
                return null;
            }
        }
        return null;
    }

    public static String getMsgFromCode(Throwable e) {
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            String msg;
            switch (code) {
                case 401:
                    msg = "User does not exist";
                    break;
                case 403:
                case 404:
                default:
                    msg = "Something went wrong on the server";
                    break;
            }
            return msg;
        } else return "Something went wrong on the server";
    }
}
