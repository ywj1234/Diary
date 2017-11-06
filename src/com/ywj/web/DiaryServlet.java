package com.ywj.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ywj.dao.DiaryDao;
import com.ywj.dao.DiaryTypeDao;
import com.ywj.model.Diary;
import com.ywj.util.DbUtiles;
import com.ywj.util.StringUtil;

public class DiaryServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DiaryDao diaryDao=new DiaryDao();
	ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		if ("show".equals(action)) {
			diaryShow(request, response);
		}else if("preSave".equals(action)){
			diarypreSave(request, response);
		}else if("save".equals(action)){
			diarysave(request, response);
		}else if("delete".equals(action)){
			diaryDelete(request, response);
		}
		
	}

	protected void diaryDelete (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String diaryId = request.getParameter("diaryId");
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			diaryDao.driayDelete(con, diaryId);
			request.getRequestDispatcher("main?all=true").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}

	protected void diarysave (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String typeId = request.getParameter("typeId");
		String diaryId = request.getParameter("diaryId");
		
		Diary diary = new Diary(title, content, Integer.parseInt(typeId));
		if (StringUtil.isNotEmpty(diaryId)) {
			diary.setDiaryId(Integer.parseInt(diaryId));
		}
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			int saveNums;
			if (StringUtil.isNotEmpty(diaryId)) {
				saveNums = diaryDao.diaryUpdate(con, diary);
			}else {
				saveNums = diaryDao.driayAdd(con, diary);
			}
			if (saveNums>0) {
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else {
				request.setAttribute("diary", diary);
				request.setAttribute("error", "±£¥Ê ß∞‹");
				request.setAttribute("mainPage", "diary/diarysaev.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}

	protected void diarypreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String diaryId = request.getParameter("diaryId");
		Connection con = null;
		try {
			if (StringUtil.isNotEmpty(diaryId)) {
				con = DbUtiles.getConnection();
				Diary diary = diaryDao.diaryShow(con, diaryId);
				request.setAttribute("diary", diary);
			}
			request.setAttribute("mainPage", "diary/diarysaev.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}

	protected void diaryShow(HttpServletRequest request, HttpServletResponse response) {
		String diaryId = request.getParameter("diaryId");
		Connection con = null;
		try {
			con = DbUtiles.getConnection();
			Diary diary = diaryDao.diaryShow(con, diaryId);
			request.setAttribute("diary", diary);
			System.out.println(diary.toString());
			request.setAttribute("mainPage", "diary/diaryShow.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DbUtiles.close(con);
		}
	}
}
