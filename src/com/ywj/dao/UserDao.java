package com.ywj.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.ywj.model.User;
import com.ywj.util.DbUtiles;
import com.ywj.util.MD5Util;

public class UserDao {
	ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
	
	
	public User login(Connection con,User user) throws UnsupportedEncodingException{
		
		User resultUesr = null;
		String sql = "select *from t_user where userName=? and password=?;";
		try {
			PreparedStatement pStatement = con.prepareStatement(sql);
			pStatement.setString(1, user.getUserName());
			pStatement.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
			ResultSet reSet = pStatement.executeQuery();
			if (reSet.next()) {
				resultUesr = new User();
				resultUesr.setUserId(reSet.getInt("userId"));
				resultUesr.setUserName(reSet.getString("userName"));
				resultUesr.setPassword(reSet.getString("password"));
				resultUesr.setNickName(reSet.getString("nickName"));
				resultUesr.setImageName(bundle.getString("imageFile")+reSet.getString("imageName"));
				resultUesr.setMood(reSet.getString("mood"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultUesr;
	}
	
	 
	public static void main(String[] args) throws Exception {
		UserDao dao = new UserDao();
		Connection con = null;
		con = DbUtiles.getConnection();
		User user2 = dao.login(con, new User());
		System.out.println(user2.getImageName());
		DbUtiles.close(con);
	}
}
