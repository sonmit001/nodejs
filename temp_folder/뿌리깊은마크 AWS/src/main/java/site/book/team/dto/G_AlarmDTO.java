/*
 * @Project : DeepRoot
 * @FileName : G_AlarmDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : G_AlarmDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class G_AlarmDTO {
	private String toid; // 받는이 ID
	private String fromid; // 초대자 ID
	private int gid;
	private int gaid;
	private String senddate;
	
	public G_AlarmDTO() {}

	public G_AlarmDTO(String toid, int gid, int gaid, String senddate) {
		this.toid = toid;
		this.gid = gid;
		this.gaid = gaid;
		this.senddate = senddate;
	}

	public G_AlarmDTO(String toid, String fromid, int gid, int gaid, String senddate) {
		this.toid = toid;
		this.fromid = fromid;
		this.gid = gid;
		this.gaid = gaid;
		this.senddate = senddate;
	}
	
	//Getters and Setters
	public String getToid() {return toid;}
	public void setToid(String toid) {this.toid = toid;}
	public String getFromid() {return fromid;}
	public void setFromid(String fromid) {this.fromid = fromid;}
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public int getGaid() {return gaid;}
	public void setGaid(int gaid) {this.gaid = gaid;}
	public String getSenddate() {return senddate;}
	public void setSenddate(String senddate) {this.senddate = senddate;}

	@Override
	public String toString() {
		return "G_AlarmDTO [toid=" + toid + ", fromid=" + fromid + ", gid=" + gid + ", gaid=" + gaid + ", senddate="
				+ senddate + "]";
	}
	
}
