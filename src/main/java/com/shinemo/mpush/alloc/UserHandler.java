package com.shinemo.mpush.alloc;

import com.mpush.api.Constants;
import com.mpush.tools.Jsons;
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

    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    private String userid = null;
    private String img = null;
    private String nickname = null;
    private Integer appid = null;

    static Properties properties = new Properties();

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
        Integer id;
        userid = (String) params.get("userid");
        img = (String) params.get("img");
        nickname = (String) params.get("nickname");
        appid = (Integer) params.get("appid");
        id = isExistedByAppid(appid);
        if (id==null) {
            id = insert();
        }
        return id;
    }

    private Integer insert() {
        Integer id=null;
        String sql = "insert into  user (`userid`,`img`,`nickname`,`app_id`)values (?,?,?,?);";
        Connection conn = JdbcUtils.getConn();
        try {
            ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userid);
            ps.setString(2, img);
            ps.setString(3, nickname);
            ps.setInt(4, appid);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id=1;
    }

    private Integer isExistedByAppid(Integer appid) {
        Integer id;
        String sql = "SELECT * FROM user WHERE app_id=" + appid;
        Connection conn = JdbcUtils.getConn();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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
