/*
 * @Project : DeepRoot
 * @FileName : VisitorDTO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
*/


package site.book.admin.dto;

/**
 * @Class : VisitorDTO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
 */
public class VisitorDTO {
	private int vid;
	private String vip;
	private String vtime;
	private String vrefer;
	private String vagent;
	
	public VisitorDTO() {
		super();
	}
	
	public VisitorDTO(int vid, String vip, String vtime, String vrefer, String vagent) {
		super();
		this.vid = vid;
		this.vip = vip;
		this.vtime = vtime;
		this.vrefer = vrefer;
		this.vagent = vagent;
	}
	
	//Getters and Setters
	public int getVid() {return vid;}
	public void setVid(int vid) {this.vid = vid;}
	public String getVip() {return vip;}
	public void setVip(String vip) {this.vip = vip;}
	public String getVtime() {return vtime;}
	public void setVtime(String vtime) {this.vtime = vtime;}
	public String getVrefer() {return vrefer;}
	public void setVrefer(String vrefer) {this.vrefer = vrefer;}
	public String getVagent() {return vagent;}
	public void setVagent(String vagent) {this.vagent = vagent;}

	@Override
	public String toString() {
		return "VisitorDTO [vid=" + vid + ", vip=" + vip + ", vtime=" + vtime + ", vrefer=" + vrefer + ", vagent="
				+ vagent + "]";
	}
	
}
