package com.ywj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DbUtiles {

	static final String JDBCNAME;
	static final String DBURL;
	static final String USER;
	static final String PASSWORD;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		JDBCNAME = bundle.getString("jdbcName");
		DBURL = bundle.getString("dbUrl");
		USER = bundle.getString("user");
		PASSWORD = bundle.getString("password");
	}

	static {
		try {
			Class.forName(JDBCNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���ݿ����� ͨ�������ļ���ȡ���ݣ��������.class�ļ�
	 * 
	 * @return
	 * @throws SQLException 
	 * @throws Exception
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DBURL, USER, PASSWORD);
	}

	/**
	 * �ر���Դ
	 * 
	 * @param con
	 */
	public static void close(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر���Դ
	 * 
	 * @param con
	 */
	public static void close(Statement stat, Connection con) {
		try {
			if (stat != null) {
				stat.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ر���Դ
	 * 
	 * @param con
	 */
	public static void close(PreparedStatement pre, Connection con) {
		try {
			if (pre != null) {
				pre.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
