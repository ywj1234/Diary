package com.ywj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ywj.model.Diary;
import com.ywj.model.DiaryType;
import com.ywj.util.DbUtiles;

public class DiaryTypeDao {
	
	public List<DiaryType> diarytypeCountList(Connection con) throws Exception{
		List<DiaryType> diaryTypeDao = new ArrayList<DiaryType>();
		String sql = "SELECT t_diarytype.diaryId,typeName,COUNT(t_diary.diaryId) as diaryCount FROM t_diary RIGHT JOIN t_diarytype ON t_diary.typeId=t_diarytype.diaryId GROUP BY typeName;";
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryId(resultSet.getInt("diaryId"));
			diaryType.setTypeName(resultSet.getString("typeName"));
			diaryType.setDiarycount(resultSet.getInt("diaryCount"));
			diaryTypeDao.add(diaryType);
		}
		return diaryTypeDao;
	}
	
	public List<DiaryType> diarytypeList(Connection con) throws Exception{
		List<DiaryType> diaryTypeDao = new ArrayList<DiaryType>();
		String sql = "SELECT *FROM t_diarytype";
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet resultSet = pre.executeQuery();
		while (resultSet.next()) {
			DiaryType diaryType = new DiaryType();
			diaryType.setDiaryId(resultSet.getInt("diaryId"));
			diaryType.setTypeName(resultSet.getString("typeName"));
			diaryTypeDao.add(diaryType);
		}
		return diaryTypeDao;
	}
	
	/**
	 * 添加日记类别
	 * @param con
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeAdd(Connection con,DiaryType diaryType) throws Exception{
		String sql = "INSERT INTO t_diarytype VALUES(NULL,?);";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryType.getTypeName());
		return pre.executeUpdate();
	}
	
	/**
	 * 修改日记类别
	 * @param con
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeUpdate(Connection con,DiaryType diaryType) throws Exception{
		String sql = "UPDATE t_diarytype SET typeName=? WHERE diaryId=?;";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryType.getTypeName());
		pre.setInt(2, diaryType.getDiaryId());
		return pre.executeUpdate();
	}
	
	public DiaryType diaryTypeShow(Connection con,String diaryTypeId)throws Exception{
		String sql = "SELECT *from t_diarytype where diaryId = ?";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryTypeId);
		ResultSet resultSet = pre.executeQuery();
		DiaryType diaryType = new DiaryType();
		if (resultSet.next()) {
			diaryType.setDiaryId(resultSet.getInt("diaryId"));
			diaryType.setTypeName(resultSet.getString("typeName"));
		}
		return diaryType;
	}
	
	/**
	 * 删除日记类别,检查日记类别下是否有日记
	 * @param con
	 * @param diaryType
	 * @return
	 * @throws Exception
	 */
	public int diaryTypeDelete(Connection con,String diaryTypeId)throws Exception{
		String sql = "delete from t_diarytype where diaryId = ?";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.setString(1, diaryTypeId);
		return pre.executeUpdate();
	}
	
}
