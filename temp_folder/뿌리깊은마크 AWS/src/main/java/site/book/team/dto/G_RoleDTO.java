package site.book.team.dto;

public class G_RoleDTO {
	private String grid;
	private String grname;
	
	
	public G_RoleDTO() {}
	public G_RoleDTO(String grid, String grname) {
		this.grid = grid;
		this.grname = grname;
	}
	
	public String getGrid() {return grid;}
	public void setGrid(String grid) {this.grid = grid;}
	public String getGrname() {return grname;}
	public void setGrname(String grname) {this.grname = grname;}
	
	@Override
	public String toString() {
		return "G_RoleDTO [grid=" + grid + ", grname=" + grname + "]";
	}
}
