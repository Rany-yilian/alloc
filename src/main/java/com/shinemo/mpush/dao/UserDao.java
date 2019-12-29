package com.shinemo.mpush.dao;

import com.shinemo.mpush.bean.User;

import java.util.List;

public class UserDao extends BaseDao<User> {

    public int update(User user) {
        String sql = "update user set userid=?,img=?,nickname=? where id=?";
        Object[] args = {user.getUserid(), user.getImg(), user.getNickname(), user.getId()};
        return super.runSql(sql, args);
    }

    public int insert(User user) {
        String sql = "insert into  user (`userid`,`img`,`nickname`,`app_id`)values (?,?,?,?);";
        Object[] args = {user.getUserid(), user.getImg(), user.getNickname(), user.getApp_id()};
        return super.runSql(sql, args);
    }

    public List<User> all() {
        String sql = "select * from user";
        List<User> list = super.runQuery(sql, new User());
        return list;
    }

    public User queryById(int id) {
        User user = null;
        String sql = "select * from user where id=?";
        List<User> list = super.runQuery(sql, new User(), id);
        int num = list.size();
        user = num > 0 ? list.get(0) : null;
        return user;
    }

    public User queryByRegist(String userid, int appid) {
        User user = null;
        String sql = "select * from user where userid=? and app_id=?";
        List<User> list = super.runQuery(sql, new User(), userid, appid);
        int num = list.size();
        user = num > 0 ? list.get(0) : null;
        return user;
    }

}
