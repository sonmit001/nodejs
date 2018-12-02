/*
 * @Project : DeepRoot
 * @FileName : A_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김태웅
*/


package site.book.admin.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.admin.dao.A_BookDAO;
import site.book.admin.dto.A_BookDTO;

/**
 * @Class : A_BookService.java
 * @Date : 2018. 6. 13.
 * @Author : 김희준
 */
@Service
public class A_BookService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 전체 URL 가져오기
	public List<A_BookDTO> getBooks() {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectAllBook();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	// URL 하나 가져오기
	public A_BookDTO getBook(int abid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		A_BookDTO book = null;
		
		try {
			book = bookDAO.selectBook(abid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	// URL 추가하기
	@Transactional
	public A_BookDTO addBook(A_BookDTO book) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		
		try {
			bookDAO.insertBook(book);
			int abid = bookDAO.getMaxABID();
			book.setAbid(abid);
			screenshot(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	// URL 수정하기
	public int updateBook(A_BookDTO book) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.updateBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// URL 삭제하기
	public int deleteBook(int abid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.deleteBook(abid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 카테고리 별 URL 가져오기
	public List<A_BookDTO> getCategoryURL(int acid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectCategoryURL(acid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void screenshot(A_BookDTO book) {
		System.out.println("스케줄러 시작");
		
		String path = this.getClass().getResource("").getPath();
		int index = path.indexOf("WEB-INF");
		String realpath = path.substring(0, index);
		
		// chormedriver 경로
		String exePath = realpath + "\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);

		// chormeOption
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // 창 없는 옵션
		options.addArguments("--hide-scrollbars"); // 스크롤바 없애는 옵션
		options.addArguments("window-size=1080x1080"); // 화면 크기 옵션
		options.addArguments("disable-gpu"); // 성능
		
		// 폴더 없을시 생성
		File forder = new File(realpath + "\\images\\homepage");
		if(!forder.exists()) {
			forder.mkdir();
		}
		
		WebDriver driver = new ChromeDriver(options);
		driver.get(book.getUrl());
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		// 파일 저장하기
		try {
			FileUtils.copyFile(scrFile, new File(realpath + "\\images\\homepage\\" + book.getAbid() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		driver.quit();
		
		System.out.println("스케줄러 끝");
	}
	
	
	/* 2018-06-11(MON): 김태웅 추가 */
	// Main에서 URL 전체 보기
	public List<A_BookDTO> getMainBooks() {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectAllBookMain();
		} catch (ClassNotFoundException | SQLException e) {
			/*e.printStackTrace();*/
		}
		
		return list;
	}
	// Main Table URL Click +1
	public int clickURL(String abid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int result = 0;
		
		try {
			result = bookDAO.clickURL(Integer.parseInt(abid));
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
