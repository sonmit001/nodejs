/*
 * @Project : DeepRoot
 * @FileName : NoticeDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.dto;

/**
 * @Class : NoticeDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class NoticeDTO {
	private int nid;
	private String ncontent;
	private String regdate;
	
	public NoticeDTO() {}

	public NoticeDTO(int nid, String ncontent, String regdate) {
		this.nid = nid;
		this.ncontent = ncontent;
		this.regdate = regdate;
	}
	
	//Getters and Setters
	public int getNid() {return nid;}
	public void setNid(int nid) {this.nid = nid;}
	public String getNcontent() {return ncontent;}
	public void setNcontent(String ncontent) {this.ncontent = ncontent;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}

	@Override
	public String toString() {
		return "NoticeDTO [nid=" + nid + ", ncontent=" + ncontent + ", regdate=" + regdate + "]";
	}
	
}
