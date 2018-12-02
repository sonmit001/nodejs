/*
 * @Project : DeepRoot
 * @FileName : TopDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.social.dto;

/**
 * @Class : TopDTO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public class TopDTO {
	private String url;
	private String urlname;
	private int ucount;
	
	public TopDTO() {}

	public TopDTO(String url, String urlname, int ucount) {
		this.url = url;
		this.urlname = urlname;
		this.ucount = ucount;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlname() {
		return urlname;
	}
	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}
	public int getUcount() {
		return ucount;
	}
	public void setUcount(int ucount) {
		this.ucount = ucount;
	}

	@Override
	public String toString() {
		return "TopDTO [url=" + url + ", urlname=" + urlname + ", ucount=" + ucount + "]";
	}
	
}
