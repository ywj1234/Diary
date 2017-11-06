package com.ywj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ywj.model.Diary;
import com.ywj.model.DiaryType;
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
			Diary diary= new Diary();
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
	/**
	 * 返回日期总数并且排序
	 * @param con
	 * @return
	 * @throws Exception
	 */
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
	
	/**
	 * 根据id获取日记的详细信息
	 * @param con
	 * @param diaryId
	 * @return
	 * @throws Exception 
	 */
	public Diary diaryShow(Connection con,String diaryId) throws Exception{
		
		String sql = "select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryId and t1.diaryId=?";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryId);
		ResultSet resultSet = pre.executeQuery();
		Diary diary = new Diary();
		if (resultSet.next()) {
			diary.setDiaryId(resultSet.getInt("diaryId"));
			diary.setTitle(resultSet.getString("title"));
			diary.setContent(resultSet.getString("content"));
			diary.setTypeId(resultSet.getShort("typeId"));
			diary.setTypeName(resultSet.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(resultSet.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
		}
		return diary;
	}
	
	/**
	 * 新增日记
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception 
	 */
	public int driayAdd(Connection con,Diary diary) throws Exception{
		String sql = "insert into t_diary values(null,?,?,?,now());";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diary.getTitle());
		pre.setString(2, diary.getContent());
		pre.setInt(3, diary.getTypeId());
		return pre.executeUpdate();
	}
	
	/**
	 * 删除日记
	 * @param con
	 * @param diary
	 * @return
	 * @throws Exception 
	 */
	public int driayDelete(Connection con,String diaryId) throws Exception{
		String sql = "DELETE FROM t_diary WHERE diaryId=?";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryId);
		return pre.executeUpdate();
	}
	
	/**
	 * 修改日记
	 * @param con
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryUpdate(Connection con,Diary diary) throws Exception{
		String sql = "UPDATE t_diary SET title=?,content=?,typeId=? WHERE diaryId=?";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diary.getTitle());
		pre.setString(2, diary.getContent());
		pre.setInt(3, diary.getTypeId());
		pre.setInt(4, diary.getDiaryId());
		return pre.executeUpdate();
	}
	
	//检查日记类别下是否有日记
	public boolean existDiaryWithTypeId(Connection con,String typeId)throws Exception{
		String sql="select * from t_diary where typeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, typeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		DiaryDao dao = new DiaryDao();
		Connection con = null;
		con = DbUtiles.getConnection();
		int i = dao.driayDelete(con,"37");
		System.out.println(i);
		DbUtiles.close(con);
	}
}
