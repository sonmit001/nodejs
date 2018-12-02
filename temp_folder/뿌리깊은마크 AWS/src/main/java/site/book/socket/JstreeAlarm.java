/*
 * @Project : DeepRoot
 * @FileName : ChatMessage.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
*/


package site.book.socket;

public class JstreeAlarm {
	private String nname;
	private String content;
	
	public String getNname() {
		return nname;
	}
	public void setNname(String nname) {
		this.nname = nname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "JstreeAlarm [nname=" + nname + ", content=" + content + "]";
	}
	
	
}

