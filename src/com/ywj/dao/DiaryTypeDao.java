package com.ywj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ywj.model.DiaryType;

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
}
