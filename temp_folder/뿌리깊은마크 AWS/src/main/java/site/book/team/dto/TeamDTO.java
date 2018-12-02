/*
 * @Project : DeepRoot
 * @FileName : TeamDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dto;

/**
 * @Class : TeamDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class TeamDTO {
	private int gid;
	private String gname;
	private String htag;
	private String regdate;
	private String duedate;
	private int ccount;
	private int grid;
	private int view;
	
	public TeamDTO() {}

	public TeamDTO(int gid, String gname, String htag, String regdate, String duedate, int ccount, int grid, int view) {
		this.gid = gid;
		this.gname = gname;
		this.htag = htag;
		this.regdate = regdate;
		this.duedate = duedate;
		this.ccount = ccount;
		this.grid = grid;
		this.view = view;
	}
	
	public int getGid() {return gid;}
	public void setGid(int gid) {this.gid = gid;}
	public String getGname() {return gname;}
	public void setGname(String gname) {this.gname = gname;}
	public String getHtag() {return htag;}
	public void setHtag(String htag) {this.htag = htag;}
	public String getRegdate() {return regdate;}
	public void setRegdate(String regdate) {this.regdate = regdate;}
	public String getDuedate() {return duedate;}
	public void setDuedate(String duedate) {this.duedate = duedate;}
	public int getCcount() {return ccount;}
	public void setCcount(int ccount) {this.ccount = ccount;}
	public int getGrid() {return grid;}
	public void setGrid(int grid) {this.grid = grid;}
	public int getView() {return view;}
	public void setView(int view) {this.view = view;}

	@Override
	public String toString() {
		return "TeamDTO [gid=" + gid + ", gname=" + gname + ", htag=" + htag + ", regdate=" + regdate + ", duedate="
				+ duedate + ", ccount=" + ccount + ", grid=" + grid + ", view=" + view +"]";
	}

}
