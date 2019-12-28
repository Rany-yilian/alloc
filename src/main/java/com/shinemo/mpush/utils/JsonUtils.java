package com.shinemo.mpush.utils;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class JsonUtils {

    public static void renderJson(HttpExchange httpExchange,String response) {
        Headers headers = httpExchange.getResponseHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("Access-Control-Allow-Origin", "*");
        headers.set("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        headers.set("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept");
        try {
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
