package com.ywj.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ywj.dao.DiaryDao;
import com.ywj.dao.DiaryTypeDao;
import com.ywj.model.DiaryType;
import com.ywj.util.DbUtiles;
import com.ywj.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	DiaryDao diaryDao=new DiaryDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("list".equals(action)) {
			diaryTypeList(request, response);
		}else if ("preSave".endsWith(action)) {
			diaryPreSave(request, response);
		}else if ("save".endsWith(action)) {
			diaryTypeSave(request, response);
		}else if ("delete".endsWith(action)) {
			diaryTypedelete(request, response);
		}
	}
	private void diaryTypedelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String diaryTypeId = request.getParameter("diaryTypeId");
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			if (diaryDao.existDiaryWithTypeId(con, diaryTypeId)) {
				request.setAttribute("error", "日记类别下有日记，不能删除！！！了解？");
			}else {
				diaryTypeDao.diaryTypeDelete(con, diaryTypeId);
			}
			request.getRequestDispatcher("diaryType?action=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}
	
	protected void diaryTypeSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String diaryTypeId=request.getParameter("diaryId");
		String typeName=request.getParameter("typeName");
		DiaryType diaryType=new DiaryType(typeName);
		if(StringUtil.isNotEmpty(diaryTypeId)){
			diaryType.setDiaryId(Integer.parseInt(diaryTypeId));
		}
		Connection con=null;
		try{
			con=DbUtiles.getConnection();
			int saveNum=0;
			if(StringUtil.isNotEmpty(diaryTypeId)){
				saveNum=diaryTypeDao.diaryTypeUpdate(con, diaryType);
			}else{
				saveNum=diaryTypeDao.diaryTypeAdd(con, diaryType);
			}
			if(saveNum>0){
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}else{
				request.setAttribute("diaryType", diaryType);
				request.setAttribute("error", "保存失败！");
				request.setAttribute("mainPage", "diaryType/diaryTypesss.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				DbUtiles.close(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void diaryPreSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String diaryId = request.getParameter("diaryId");
		if (StringUtil.isNotEmpty(diaryId)) {
			Connection con = null;
			try {
				con = DbUtiles.getConnection();
				DiaryType diaryType= diaryTypeDao.diaryTypeShow(con, diaryId);
				request.setAttribute("diaryType", diaryType);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				DbUtiles.close(con);
			}
		}
		request.setAttribute("mainPage", "diaryType/diaryTypesss.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}

	protected void diaryTypeList(HttpServletRequest request, HttpServletResponse response) {
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			List<DiaryType> diaryTypeList = diaryTypeDao.diarytypeList(con);
			request.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}
}
