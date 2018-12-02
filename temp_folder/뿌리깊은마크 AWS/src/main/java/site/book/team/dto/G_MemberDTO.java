/*
 * @Project : DeepRoot
 * @FileName : G_MemberDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : G_MemberDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class G_MemberDTO {
	// Alarm의 경우 자신의  GRID
	private int mygrid;
	
	// Alarm의 경우 대상  GRID 
	private String uid;
	private int gid;
	private int ccount;
	private int grid;
	private String nname;
	private String profile;
	

	public G_MemberDTO() {}

	
	public G_MemberDTO(int mygrid, String uid, int gid, int ccount, int grid, String nname, String profile) {
		super();
		this.mygrid = mygrid;
		this.uid = uid;
		this.gid = gid;
		this.ccount = ccount;
		this.grid = grid;
		this.nname = nname;
		this.profile = profile;
	}
	
	public G_MemberDTO(String uid, int gid) {
		this.uid = uid;
		this.gid = gid;
	}

	//Getters and Setters
	public int getMygrid() {return mygrid;}
	public void setMygrid(int mygrid) {this.mygrid = mygrid;}
	public String getUid() {return uid;}
	public void setUid(String uid) {this.uid = uid;}
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public int getCcount() {return ccount;}
	public void setCcount(int ccount) {this.ccount = ccount;}
	public int getGrid() {return grid;}
	public void setGrid(int grid) {this.grid = grid;}
	public String getNname() {return nname;}
	public void setNname(String nname) {this.nname = nname;}
	public String getProfile() {return profile;}
	public void setProfile(String profile) {this.profile = profile;}

	@Override
	public String toString() {
		return "G_MemberDTO [uid=" + uid + ", gid=" + gid + ", ccount=" + ccount + ", grid=" + grid + ", nname=" + nname
				+ ", profile=" + profile + "]";
	}
	
}

