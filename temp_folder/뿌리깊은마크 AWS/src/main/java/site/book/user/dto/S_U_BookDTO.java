/*
 * @Project : DeepRoot
 * @FileName : S_U_BookDTO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
*/


package site.book.user.dto;

/**
 * @Class : S_U_BookDTO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public class S_U_BookDTO {
	private int ubid;
	private String url;
	private int view;
	private String uid;
	private String nname;
	private String sname;
	private String htag;
	private String sdate;

	public S_U_BookDTO() {}
	
	public S_U_BookDTO(int ubid, String url, int view, String uid, String nname, String sname, String htag,
			String sdate) {
		this.ubid = ubid;
		this.url = url;
		this.view = view;
		this.uid = uid;
		this.nname = nname;
		this.sname = sname;
		this.htag = htag;
		this.sdate = sdate;
	}
	
	//Getters and Setters
	public int getUbid() {return ubid;}
	public void setUbid(int ubid) {this.ubid = ubid;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public int getView() {return view;}
	public void setView(int view) {this.view = view;}
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}
	public String getNname() {return nname;}
	public void setNname(String nname) {this.nname = nname;}
	public String getSname() {return sname;}
	public void setSname(String sname) {this.sname = sname;}
	public String getHtag() {return htag;}
	public void setHtag(String htag) {this.htag = htag;}
	public String getSdate() {return sdate;}
	public void setSdate(String sdate) {this.sdate = sdate;}

	@Override
	public String toString() {
		return "S_U_BookDTO [ubid=" + ubid + ", url=" + url + ", view=" + view + ", uid=" + uid + ", nname=" + nname
				+ ", sname=" + sname + ", htag=" + htag + ", sdate=" + sdate + "]";
	}

	

	
}
