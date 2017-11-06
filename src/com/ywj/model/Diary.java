package com.ywj.model;

import java.util.Date;

public class Diary {
	private int diaryId;
	private String title;
	private String content;
	private int typeId = -1;
	private Date releaseDate;
	private String releaseDatestr;
	private String typeName;
	
	
	
	public Diary() {
		super();
	}
	public Diary(String title, String content, int typeId) {
		super();
		this.title = title;
		this.content = content;
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	private int diaryCount;
	
	
	public int getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	public String getReleaseDatestr() {
		return releaseDatestr;
	}
	public void setReleaseDatestr(String releaseDatestr) {
		this.releaseDatestr = releaseDatestr;
	}
	
	public int getDiaryCount() {
		return diaryCount;
	}
	public void setDiaryCount(int diaryCount) {
		this.diaryCount = diaryCount;
	}
	
	@Override
	public String toString() {
		return "Diary [diaryId=" + diaryId + ", title=" + title + ", content="
				+ content + ", releaseDate="
				+ releaseDate + "]";
	}
}
