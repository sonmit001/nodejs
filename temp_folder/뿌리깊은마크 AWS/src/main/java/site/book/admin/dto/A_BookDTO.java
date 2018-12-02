/*
 * @Project : DeepRoot
 * @FileName : A_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.dto;

/**
 * @Class : A_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class A_BookDTO {
	private int abid;
	private String url;
	private String urlname;
	private String regdate;
	private int view;
	private int acid;
	private String acname;
	
	public A_BookDTO() {}
	
	public A_BookDTO(int abid, String url, String urlname, String regdate, int view, int acid, String acname) {
		this.abid = abid;
		this.url = url;
		this.urlname = urlname;
		this.regdate = regdate;
		this.view = view;
		this.acid = acid;
		this.acname = acname;
	}
	
	//Getters and Setters
	public int getAbid() {return abid;}
	public void setAbid(int abid) {this.abid = abid;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getUrlname() {return urlname;}
	public void setUrlname(String urlname) {this.urlname = urlname;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
	public int getView() {return view;}
	public void setView(int view) {this.view = view;}
	public int getAcid() {return acid;}
	public void setAcid(int acid) {this.acid = acid;}
	public String getAcname() {return acname;}
	public void setAcname(String acname) {this.acname = acname;}

	@Override
	public String toString() {
		return "A_BookDTO [abid=" + abid + ", url=" + url + ", urlname=" + urlname + ", regdate=" + regdate + ", view="
				+ view + ", acid=" + acid + ", acname=" + acname + "]";
	}
	
}
