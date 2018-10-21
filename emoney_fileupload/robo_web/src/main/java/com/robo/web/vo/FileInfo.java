package com.robo.web.vo;

import org.springframework.web.multipart.MultipartFile;

public class FileInfo {
	private MultipartFile filedata;
	String attach_id;
	long mtext_id;
	private String org_name;
	private String new_name;
	private String path;
	private long fsize;
	int down_cnt;
	
	public MultipartFile getFiledata() {
		return filedata;
	}
	public void setFiledata(MultipartFile filedata) {
		this.filedata = filedata;
	}
	
	public String getAttach_id() {
		return attach_id;
	}
	public void setAttach_id(String attach_id) {
		this.attach_id = attach_id;
	}
	public long getMtext_id() {
		return mtext_id;
	}
	public void setMtext_id(long mtext_id) {
		this.mtext_id = mtext_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getNew_name() {
		return new_name;
	}
	public void setNew_name(String new_name) {
		this.new_name = new_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getFsize() {
		return fsize;
	}
	public void setFsize(long fsize) {
		this.fsize = fsize;
	}
	public int getDown_cnt() {
		return down_cnt;
	}
	public void setDown_cnt(int down_cnt) {
		this.down_cnt = down_cnt;
	}
	@Override
	public String toString() {
		return "FileInfo [filedata=" + filedata + ", attach_id=" + attach_id + ", mtext_id=" + mtext_id + ", org_name="
				+ org_name + ", new_name=" + new_name + ", path=" + path + ", fsize=" + fsize + ", down_cnt=" + down_cnt
				+ "]";
	}
}
