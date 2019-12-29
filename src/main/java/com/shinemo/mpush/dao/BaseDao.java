package com.shinemo.mpush.dao;

import com.shinemo.mpush.utils.JdbcUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao<T>{

    private Connection conn;

    private PreparedStatement pstmt;

    private ResultSet rs;

    public int runSql(String sql,Object...args){
        int flag = -1;
        conn = JdbcUtils.getConn();
        try{
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return id;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    public List<T> runQuery(String sql,T t,Object...args){
        Class clazz = t.getClass();
        //获取反射属性
        Field [] field = clazz.getDeclaredFields();
        //实例化数组存放真实属性
        String [] realField = new String[field.length];
        for (int i=0;i<field.length;i++){
            int index = field[i].toString().lastIndexOf(".");
            realField[i] = field[i].toString().substring(index+1);
        }
        List<T> list = new ArrayList<>();
        conn = JdbcUtils.getConn();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                T ot = (T)clazz.newInstance();
                for (int i =0;i<realField.length;i++){
                    Field f = clazz.getDeclaredField(realField[i]);
                    //通过反射设置属性为可访问
                    f.setAccessible(true);
                    f.set(ot,rs.getObject(realField[i]));
                }
                list.add(ot);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
