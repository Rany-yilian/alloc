package com.shinemo.mpush.dao;

import com.shinemo.mpush.bean.Auth;

import java.util.List;

public class AuthDao extends BaseDao<Auth> {

    public int update(Auth user) {
        String sql = "update auth set userid=?,img=?,nickname=? where id=?";
        Object[] args = {user.getUserid(), user.getImg(), user.getNickname(), user.getId()};
        return super.runSql(sql, args);
    }

    public int insert(Auth user) {
        String sql = "insert into  auth (`userid`,`img`,`nickname`,`app_id`)values (?,?,?,?);";
        Object[] args = {user.getUserid(), user.getImg(), user.getNickname(), user.getApp_id()};
        return super.runSql(sql, args);
    }

    public List<Auth> all() {
        String sql = "select * from auth";
        List<Auth> list = super.runQuery(sql, new Auth());
        return list;
    }

    public Auth queryById(int id) {
        Auth user = null;
        String sql = "select * from auth where id=?";
        List<Auth> list = super.runQuery(sql, new Auth(), id);
        int num = list.size();
        user = num > 0 ? list.get(0) : null;
        return user;
    }

    public Auth queryByRegist(String userid, int appid) {
        Auth user = null;
        String sql = "select * from auth where userid=? and app_id=?";
        List<Auth> list = super.runQuery(sql, new Auth(), userid, appid);
        int num = list.size();
        user = num > 0 ? list.get(0) : null;
        return user;
    }

}
