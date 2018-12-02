/*
 * @Project : DeepRoot
 * @FileName : A_CategoryDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/

package site.book.admin.dto;

/**
 * @Class : A_CategoryDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class A_CategoryDTO {
	private int acid;
	private String acname;
	private String color;
	
	public A_CategoryDTO() {}
	
	public A_CategoryDTO(int acid, String acname, String color) {
		this.acid = acid;
		this.acname = acname;
		this.color = color;
	}

	//Getters and Setters
	public int getAcid() {return acid;}
	public void setAcid(int acid) {this.acid = acid;}
	public String getAcname() {return acname;}
	public void setAcname(String acname) {this.acname = acname;}
	public String getColor() {return color;}
	public void setColor(String color) {this.color = color;}

	@Override
	public String toString() {
		return "A_CategoryDTO [acid=" + acid + ", acname=" + acname + ", color=" + color + "]";
	}

}
