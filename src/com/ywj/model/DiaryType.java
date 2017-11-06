package com.ywj.model;

public class DiaryType {
	private int diaryId;
	private String typeName;
	private int diarycount;
	
	
	public DiaryType() {
		super();
	}
	
	public DiaryType(String typeName) {
		super();
		this.typeName = typeName;
	}
	
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getDiarycount() {
		return diarycount;
	}
	public void setDiarycount(int diarycount) {
		this.diarycount = diarycount;
	}
	
	
}
