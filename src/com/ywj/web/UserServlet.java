package com.ywj.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import com.ywj.dao.UserDao;
import com.ywj.model.User;
import com.ywj.util.DateUtil;
import com.ywj.util.DbUtiles;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao = new UserDao();
	private ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("preSave".equals(action)) {
			userPreSave(request, response);
		} else if("save".equals(action)) {
			userSave(request, response);
		}
	}

	private void userSave(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator<FileItem> itr=items.iterator();
		
		HttpSession session = request.getSession();
		
		User user= (User)session.getAttribute("resultUser");
		//≈–∂œÕº∆¨ «∑ÒÃÊªª
		boolean imageChange = false;
		while(itr.hasNext()){
			FileItem item=(FileItem)itr.next();
			if(item.isFormField()){
				String fieldName=item.getFieldName();
				if("nickName".equals(fieldName)){
					user.setNickName(item.getString("utf-8"));
				}
				if("mood".equals(fieldName)){
					user.setMood(item.getString("utf-8"));
				}
			}else if(!"".equals(item.getName())){
				try{
					imageChange = true; 
					String imageName=DateUtil.getCurrentDateStr();
					user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
					String filePath=bundle.getString("imagePath")+imageName+"."+item.getName().split("\\.")[1];
					item.write(new File(filePath));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if (!imageChange) {
			user.setImageName(user.getImageName().replaceFirst(bundle.getString("imageFile"), ""));
		}
		
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			int saveNums = userDao.userUpdate(con, user);
			if (saveNums > 0) {
				user.setImageName(bundle.getString("imageFile")+user.getImageName());
				session.setAttribute("resultUser", user);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else {
				request.setAttribute("resultUser", user);
				request.setAttribute("error", "±£¥Ê ß∞‹!!");
				request.setAttribute("mainPage", "user/userSavv.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
		
	}

	private void userPreSave(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mainPage", "user/userSavv.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}
}
