package com.ywj.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.ywj.model.User;
import com.ywj.util.MD5Util;

public class UserDao {
	ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
	
	/**
	 * 返回用户信息
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
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
	
	
	/**
	 * 修改个人信息
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public int userUpdate(Connection con,User user) throws Exception{
		String sql = "update t_user set nickName=?,imageName=?,mood=? where userId=?";
		PreparedStatement pStatement = con.prepareStatement(sql);
		pStatement.setString(1, user.getNickName());
		pStatement.setString(2, user.getImageName());
		pStatement.setString(3, user.getMood()); 
		pStatement.setInt(4, user.getUserId());
		return pStatement.executeUpdate();
	}
}
