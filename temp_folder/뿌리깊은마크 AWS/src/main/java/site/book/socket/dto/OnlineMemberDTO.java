package site.book.socket.dto;

public class OnlineMemberDTO {
	private String nname;
	private String status;
	
	public OnlineMemberDTO() {}
	public OnlineMemberDTO(String nname, String status) {
		this.nname = nname;
		this.status = status;
	}
	public String getNname() {return nname;}
	public void setNname(String nname) {this.nname = nname;}
	public String getStatus() {return status;}
	public void setStatus(String status) {this.status = status;}
	
	@Override
	public String toString() {
		return "OnlineMemberDTO [nname=" + nname + ", status=" + status + "]";
	}
}
