package com.robo.web.vo;

public class ReplyTextInfo {
	
	private long mtext_id = 0;
	private long rtext_id = 0;
	private long accnt_id = 0;
	private String contents = "";
	private String nickname = "";
	private String create_time = "";
	private int agree_cnt = 0;
	private String is_delete = "";
	private int did = 0;
	
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public long getMtext_id() {
		return mtext_id;
	}
	public void setMtext_id(long mtext_id) {
		this.mtext_id = mtext_id;
	}
	public long getRtext_id() {
		return rtext_id;
	}
	public void setRtext_id(long rtext_id) {
		this.rtext_id = rtext_id;
	}
	public long getAccnt_id() {
		return accnt_id;
	}
	public void setAccnt_id(long accnt_id) {
		this.accnt_id = accnt_id;
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public int getAgree_cnt() {
		return agree_cnt;
	}
	public void setAgree_cnt(int agree_cnt) {
		this.agree_cnt = agree_cnt;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	@Override
	public String toString() {
		return "ReplyTextInfo [mtext_id=" + mtext_id + ", rtext_id=" + rtext_id + ", accnt_id=" + accnt_id
				+ ", contents=" + contents + ", nickname=" + nickname + ", create_time=" + create_time + ", agree_cnt="
				+ agree_cnt + ", is_delete=" + is_delete + ", did=" + did + "]";
	}
	
}
