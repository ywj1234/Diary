package com.ywj.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ywj.dao.UserDao;
import com.ywj.model.User;
import com.ywj.util.DbUtiles;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		Connection con = null;
		User user = new User(userName, password); 
		try {
			con = DbUtiles.getConnection();
			User resultUser = userDao.login(con,user);
			//检查用户是否存在
			if (resultUser == null) {
				request.setAttribute("user",user);
				request.setAttribute("error","用户名或密码错误!");
				//内部转发
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}else {
				if ("remember-me".equals(remember)) {
					rememberMe(userName, password, response);
				}
				session.setAttribute("resultUser", resultUser);
				request.getRequestDispatcher("main").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}
	
	private void rememberMe(String userName,String password,HttpServletResponse response){
		Cookie cookie = new Cookie("user", userName+"-"+password);
		cookie.setMaxAge(1*60*60*24*7);
		response.addCookie(cookie);
	}
}
