package com.robo.web.vo;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardInfo {
	
	private long mtext_id = 0;
	private long parent_id = 0;
	private String title = "";
	private String contents = "";
	private String nickname = "";
	private long accnt_id = 0;
	private String create_time = "";
	private int view_cnt = 0;
	private String update_time = "";
	private String type = "";
	private String attachfile[];
	private ArrayList<Object> fList;
	private int board_id = 0;
	private String[] json;
	private String arr_num = "";
	private int onlytext = 0;
	private int agree_cnt = 0;
	
	
	
	public int getAgree_cnt() {
		return agree_cnt;
	}
	public void setAgree_cnt(int agree_cnt) {
		this.agree_cnt = agree_cnt;
	}
	public int getOnlytext() {
		return onlytext;
	}
	public void setOnlytext(int onlytext) {
		this.onlytext = onlytext;
	}
	public String getArr_num() {
		return arr_num;
	}
	public void setArr_num(String arr_num) {
		this.arr_num = arr_num;
	}
	public String[] getJson() {
		return json;
	}
	public void setJson(String[] json) {
		this.json = json;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public ArrayList<Object> getfList() {
		return fList;
	}
	public void setfList(ArrayList<Object> fList) {
		this.fList = fList;
	}
	public String[] getAttachfile() {
		return attachfile;
	}
	public void setAttachfile(String[] attachfile) {
		this.attachfile = attachfile;
	}
	public long getMtext_id() {
		return mtext_id;
	}
	public void setMtext_id(long mtext_id) {
		this.mtext_id = mtext_id;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public long getAccnt_id() {
		return accnt_id;
	}
	public void setAccnt_id(long accnt_id) {
		this.accnt_id = accnt_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "BoardInfo [mtext_id=" + mtext_id + ", parent_id=" + parent_id + ", title=" + title + ", contents="
				+ contents + ", nickname=" + nickname + ", accnt_id=" + accnt_id + ", create_time=" + create_time
				+ ", view_cnt=" + view_cnt + ", update_time=" + update_time + ", type=" + type + ", attachfile="
				+ Arrays.toString(attachfile) + ", fList=" + fList + ", board_id=" + board_id + ", json="
				+ Arrays.toString(json) + ", arr_num=" + arr_num + ", onlytext=" + onlytext + ", agree_cnt=" + agree_cnt
				+ "]";
	}
	
}
