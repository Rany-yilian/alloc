package com.shinemo.mpush.alloc;

import com.mpush.api.Constants;
import com.mpush.tools.Jsons;
import com.shinemo.mpush.bean.User;
import com.shinemo.mpush.dao.UserDao;
import com.shinemo.mpush.utils.EncryptUtils;
import com.shinemo.mpush.utils.JdbcUtils;
import com.shinemo.mpush.utils.JsonUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

final class UserHandler implements HttpHandler {

    private String userid = null;
    private String img = null;
    private String nickname = null;
    private Integer appid = null;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body = new String(readBody(httpExchange), Constants.UTF_8);
        Map<String, Object> params = Jsons.fromJson(body, Map.class);
        Integer id = userRegist(params);
        try {
            String encrypted = EncryptUtils.aesEncrypt("45454");
            String response = "{\"code\":\"1\",\"msg\":\"成功\",\"data\":{\"token\":\""+encrypted+"\",\"id\":\""+id+"\"}}";
            JsonUtils.renderJson(httpExchange, response);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private Integer userRegist(Map<String, Object> params) {
        int id;
        String userid = (String) params.get("userid");
        int appid = (int) params.get("appid");
        UserDao userDao = new UserDao();
        User resUser = userDao.queryByRegist(userid,appid);
        if(resUser==null){
            id = insert(params);
            return id;
        }
        return resUser.getId();
    }

    private Integer insert(Map<String,Object> params) {
        userid = (String) params.get("userid");
        img = (String) params.get("img");
        nickname = (String) params.get("nickname");
        appid = (Integer) params.get("appid");
        User user = new User();
        user.setUserid(userid);
        user.setNickname(nickname);
        user.setImg(img);
        user.setApp_id(appid);
        UserDao userDao = new UserDao();
        int id = userDao.insert(user);
        return id;
    }

    private byte[] readBody(HttpExchange httpExchange) throws IOException {
        InputStream in = httpExchange.getRequestBody();
        String length = httpExchange.getRequestHeaders().getFirst("content-length");
        if (length != null && !length.equals("0")) {
            byte[] buffer = new byte[Integer.parseInt(length)];
            in.read(buffer);
            in.close();
            return buffer;
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
            return out.toByteArray();
        }
    }
}
