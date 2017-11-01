package com.ywj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ywj.model.Diary;
import com.ywj.model.PageBean;
import com.ywj.util.DateUtil;
import com.ywj.util.DbUtiles;
import com.ywj.util.StringUtil;

public class DiaryDao {
	
	
	public List<Diary> diaryList(Connection con,PageBean pageBean,Diary s_diary)throws Exception{
		List<Diary> diaryList=new ArrayList<Diary>();
		StringBuffer sb=new StringBuffer("select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryId ");
		
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		
		if (s_diary.getTypeId()!=-1) {
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		
		if (StringUtil.isNotEmpty(s_diary.getReleaseDatestr())) {
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDatestr()+"'");
		}
		
		sb.append(" order by t1.releaseDate desc");
		if (pageBean != null) {
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Diary diary=new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	/**
	 * 获取总记录数
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public int diaryCount(Connection con,Diary s_diary) throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryId ");
		
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		
		if (s_diary.getTypeId()!=-1) {
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		
		if (StringUtil.isNotEmpty(s_diary.getReleaseDatestr())) {
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDatestr()+"'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){	 
			return rs.getInt("total");
		}else {
			return 0;
		}
	}
	
	public List<Diary> dataCountList(Connection con) throws Exception{
		List<Diary> diaryList=new ArrayList<Diary>();
		String sql = "SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDatestr,COUNT(*) AS diaryCount FROM t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC";
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next()) {
			Diary diary=new Diary();
			diary.setReleaseDatestr(resultSet.getString("releaseDatestr"));
			diary.setDiaryCount(resultSet.getInt("diaryCount"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		DiaryDao dao = new DiaryDao();
		Connection con = null;
		con = DbUtiles.getConnection();
		List<Diary> list = dao.dataCountList(con);
		for (Diary diary : list) {
			System.out.println(diary.getReleaseDatestr()+" "+diary.getDiaryCount());
		}
		DbUtiles.close(con);
	}
}
