/*
 * @Project : DeepRoot
 * @FileName : G_AlarmDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.socket.dto;

/**
 * @Class : G_AlarmDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
/**
 * @author Bit
 *
 */
public class SocketAlarmDTO {
	private String gid;
	private String toid; 	// 받는이 ID
	private String fromid; 	// 초대자 ID
	private String gname;	// 그룹명
	private String gmemo;	// 메모 종류
	private String senddate;// 보낸 날짜
	
	public SocketAlarmDTO() {}

	public SocketAlarmDTO(String gid, String toid, String fromid, String gname, String gmemo, String senddate) {
		this.gid = gid;
		this.toid = toid;
		this.fromid = fromid;
		this.gname = gname;
		this.gmemo = gmemo;
		this.senddate = senddate;
	}
	
	
	public String getGid() {return gid;}
	public void setGid(String gid) {this.gid = gid;}
	public String getToid() {return toid;}
	public void setToid(String toid) {this.toid = toid;}
	public String getFromid() {return fromid;}
	public void setFromid(String fromid) {this.fromid = fromid;}
	public String getGname() {return gname;}
	public void setGname(String gname) {this.gname = gname;}
	public String getGmemo() {return gmemo;}
	public void setGmemo(String gmemo) {this.gmemo = gmemo;}
	public String getSenddate() {return senddate;}
	public void setSenddate(String senddate) {this.senddate = senddate;}

	@Override
	public String toString() {
		return "SocketAlarmDTO [gid=" + gid + ", toid=" + toid + ", fromid=" + fromid + ", gname=" + gname + ", gmemo=" + gmemo
				+ ", senddate=" + senddate + "]";
	}
}
