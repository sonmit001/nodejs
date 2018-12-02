/*
 * @Project : DeepRoot
 * @FileName : U_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.user.dto;

/**
 * @Class : U_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class U_BookDTO {
	private int ubid;
	private String url;
	private String urlname;
	private String regdate;
	private int view;
	private String uid;
	private int pid;
	private int abid;
	private String sname;
	private String htag;
	private String sdate;
	
	public U_BookDTO() {}
	
	public U_BookDTO(int ubid, String url, String urlname, String regdate, int view, String uid, int pid, int abid) {
		super();
		this.ubid = ubid;
		this.url = url;
		this.urlname = urlname;
		this.regdate = regdate;
		this.view = view;
		this.uid = uid;
		this.pid = pid;
		this.abid = abid;
	}

	public U_BookDTO(int ubid, String url, String urlname, String regdate, int view, String uid, int pid, int abid, String sname, String htag, String sdate) {
		this.ubid = ubid;
		this.url = url;
		this.urlname = urlname;
		this.regdate = regdate;
		this.view = view;
		this.uid = uid;
		this.pid = pid;
		this.abid = abid;
		this.sname = sname;
		this.htag = htag;
		this.sdate = sdate;
	}
	
	//Getters and Setters
	public int getUbid() {return ubid;}
	public void setUbid(int ubid) {this.ubid = ubid;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getUrlname() {return urlname;}
	public void setUrlname(String urlname) {this.urlname = urlname;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
	public int getView() {return view;}
	public void setView(int view) {this.view = view;}
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}
	public int getPid() {return pid;}
	public void setPid(int pid) {this.pid = pid;}
	public int getAbid() {return abid;}
	public void setAbid(int abid) {this.abid = abid;}
	public String getSname() {return sname;}
	public void setSname(String sname) {this.sname = sname;}
	public String getHtag() {return htag;}
	public void setHtag(String htag) {this.htag = htag;}
	public String getSdate() {return sdate;}
	public void setSdate(String sdate) {this.sdate = sdate;}

	@Override
	public String toString() {
		return "U_BookDTO [ubid=" + ubid + ", url=" + url + ", urlname=" + urlname + ", regdate=" + regdate + ", view="
				+ view + ", uid=" + uid + ", pid=" + pid + ", abid=" + abid + ", sname=" + sname + ", htag=" + htag
				+ ", sdate=" + sdate + "]";
	}
	
}
