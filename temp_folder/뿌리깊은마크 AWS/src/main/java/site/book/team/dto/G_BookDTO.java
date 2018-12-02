/*
 * @Project : DeepRoot
 * @FileName : G_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/

package site.book.team.dto;

/**
 * @Class : G_BookDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class G_BookDTO {
	private int gbid;
	private String url;
	private String urlname;
	private String regdate;
	private String view;
	private int gid;
	private int pid;
	private int abid;
	private String uid;
	
	public G_BookDTO() {}

	
	
	public G_BookDTO(int gbid, String url, String urlname, String regdate, String view, int gid, int pid, int abid,
			String uid) {
		super();
		this.gbid = gbid;
		this.url = url;
		this.urlname = urlname;
		this.regdate = regdate;
		this.view = view;
		this.gid = gid;
		this.pid = pid;
		this.abid = abid;
		this.uid = uid;
	}


	//Getters and Setters
	public int getGbid() {return gbid;}
	public void setGbid(int gbid) {this.gbid = gbid;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getUrlname() {return urlname;}
	public void setUrlname(String urlname) {this.urlname = urlname;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
	public String getView() {return view;}
	public void setView(String view) {this.view = view;}
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public int getPid() {return pid;}
	public void setPid(int pid) {this.pid = pid;}
	public int getAbid() {return abid;}
	public void setAbid(int abid) {this.abid = abid;}
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}



	@Override
	public String toString() {
		return "G_BookDTO [gbid=" + gbid + ", url=" + url + ", urlname=" + urlname + ", regdate=" + regdate + ", view="
				+ view + ", gid=" + gid + ", pid=" + pid + ", abid=" + abid + ", uid=" + uid + "]";
	}


}
