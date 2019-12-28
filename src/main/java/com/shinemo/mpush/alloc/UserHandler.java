package com.shinemo.mpush.alloc;

import com.mpush.api.Constants;
import com.mpush.tools.Jsons;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Map;

final class UserHandler implements HttpHandler {

    private Connection conn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    public UserHandler(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://94.191.72.51:3306/im","root","JfCRI3sZzHL_");
        }catch (ClassNotFoundException e){

        }catch (SQLException e){

        }finally {
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (Exception e){

            }
            try{
                if(ps!=null){
                    ps.close();
                }
            }catch (Exception e){

            }
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception e){

            }
        }


    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body = new String(readBody(httpExchange), Constants.UTF_8);
        Map<String,Object> params = Jsons.fromJson(body,Map.class);
        userRegist(params);
    }

    private void userRegist(Map<String,Object> params){
        String userid = (String) params.get("userid");
        String img = (String) params.get("img");
        String nickname = (String) params.get("nickname");
        insert(userid,img,nickname);
    }

    private void insert(String userid,String img,String nickname){
        try {
            String sql = "insert into  user values (?,?,?,?);";
            this.ps = this.conn.prepareStatement(sql);
            this.ps.setInt(1,111);
            this.ps.setString(2,"111");
            this.ps.setString(3,"22");
            this.ps.setString(4,"yilian");
            this.ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    private void select(){
        String sql = "select * from user";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            long start = System.currentTimeMillis();
            //建立连接
            conn = DriverManager.getConnection("jdbc:mysql://94.191.72.51:3306/im","root","JfCRI3sZzHL_");
            long end = System.currentTimeMillis();
            System.out.println("建立连接耗时:"+(end-start)+"ms 毫秒");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("id\tuserid\timg\tnickname\tapp_id");
            while (rs.next()){
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){

        } finally {
            try{
                if(rs!=null){
                    rs.close();
                }
            }catch (Exception e){

            }
            try{
                if(ps!=null){
                    ps.close();
                }
            }catch (Exception e){

            }
            try{
                if(conn!=null){
                    conn.close();
                }
            }catch (Exception e){

            }
        }
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
