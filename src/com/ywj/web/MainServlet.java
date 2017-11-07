package com.ywj.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.ywj.dao.DiaryDao;
import com.ywj.dao.DiaryTypeDao;
import com.ywj.model.Diary;
import com.ywj.model.PageBean;
import com.ywj.util.DbUtiles;
import com.ywj.util.StringUtil;

public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DiaryDao diaryDao = new DiaryDao();
	ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
	DiaryTypeDao diaryTypeDao = new DiaryTypeDao();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String s_typeId = request.getParameter("s_typeId");
		String s_releaseDataStr = request.getParameter("s_releaseDataStr");
		// ��ȡ��ǰҳ
		String page = request.getParameter("page");
		Diary diary = new Diary();
		String s_title = request.getParameter("s_title");
		String all = request.getParameter("all");

		if ("true".equals(all)) {
			if (StringUtil.isNotEmpty(s_title)) {
				diary.setTitle(s_title);
			}
			session.removeAttribute("s_releaseDataStr");
			session.removeAttribute("s_typeId");
			session.setAttribute("s_title", s_title);
		} else {
			// �ж��Ƿ�Ϊ�գ���Ϊ�վͽ�s_typeIdǰ̨����ֵ����diary��
			if (StringUtil.isNotEmpty(s_typeId)) {
				diary.setTypeId(Integer.parseInt(s_typeId));
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDataStr");
				session.removeAttribute("s_title");
			}

			if (StringUtil.isNotEmpty(s_releaseDataStr)) {
				s_releaseDataStr = new String(
						s_releaseDataStr.getBytes("ISO-8859-1"), "UTF-8");
				diary.setReleaseDatestr(s_releaseDataStr);
				session.setAttribute("s_releaseDataStr", s_releaseDataStr);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}

			if (StringUtil.isEmpty(s_typeId)) {
				Object o = session.getAttribute("s_typeId");
				if (o != null) {
					diary.setTypeId(Integer.parseInt((String) o));
				}
			}
			if (StringUtil.isEmpty(s_releaseDataStr)) {
				Object o = session.getAttribute("s_releaseDataStr");
				if (o != null) {
					diary.setReleaseDatestr((String) o);
				}
			}
			if (StringUtil.isEmpty(s_title)) {
				Object o = session.getAttribute("s_title");
				if (o != null) {
					diary.setTitle((String) o);
				}
			}
		}

		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		Connection con = null;
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(bundle.getString("pageSize")));
		try {
			con = DbUtiles.getConnection();
			List<Diary> diaryList = diaryDao.diaryList(con, pageBean, diary);
			int total = diaryDao.diaryCount(con, diary);
			String pageCode = this.genPagation(total, Integer.parseInt(page),
					Integer.parseInt(bundle.getString("pageSize")));
			request.setAttribute("pageBean", pageBean);
			request.setAttribute("total", total);
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("diaryList", diaryList);
			request.setAttribute("diaryData", diaryDao.dataCountList(con));
			session.setAttribute("diaryTypeCountList",
					diaryTypeDao.diarytypeCountList(con));
			request.setAttribute("mainPage", "diary/diaryList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtiles.close(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param totalNum
	 *            �ܼ�¼��
	 * @param currentPage
	 *            ��ǰҳ
	 * @param pageSize
	 *            ÿҳ�Ĵ�С
	 * @return
	 */
	private String genPagation(int totalNum, int currentPage, int pageSize) {
		// ��ȡ��ǰҳ����
		int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize
				: totalNum / pageSize + 1;
		StringBuffer pageCode = new StringBuffer();
		if (totalPage == 0 || totalPage == 1) {
			pageCode.append("<li class='disabled'><a href='#'>��ҳ</a></li>");
		}else {
			pageCode.append("<li><a href='main?page=1'>��ҳ</a></li>");
		}
		if (currentPage == 1 || totalPage == 0) {
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage - 1)
					+ "'>��һҳ</a></li>");
		}
		for (int i = currentPage - 2; i <= currentPage + 2; i++) {
			if (i < 1 || i > totalPage) {
				continue;
			}
			if (i == currentPage) {
				pageCode.append("<li class='active'><a href='#'>" + i
						+ "</a></li>");
			} else {
				pageCode.append("<li><a href='main?page=" + i + "'>" + i
						+ "</a></li>");
			}
		}
		if (currentPage == totalPage || totalPage == 0) {
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		} else {
			pageCode.append("<li><a href='main?page=" + (currentPage + 1)
					+ "'>��һҳ</a></li>");
		}
		if (totalPage == 0 || totalPage == 1) {
			pageCode.append("<li class='disabled'><a href='#'>βҳ</a></li>");
		}else {
			pageCode.append("<li><a href='main?page=" + totalPage + "'>βҳ</a></li>");
		}
		return pageCode.toString();
	}
}
