/*
 * @Project : DeepRoot
 * @FileName : HomepageCapture.java
 * @Date : 2018. 6. 12.
 * @Author : 김희준
*/


package site.book.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.service.A_BookService;

/**
 * @Class : HomepageCapture.java
 * @Date : 2018. 6. 12.
 * @Author : 김희준
 */

@Service
public class HomepageCapture {
	
	@Autowired
	private A_BookService a_book_service;
	
	@Scheduled(cron= "0 0 4 * * *" )
	public void screenshot() {
		System.out.println("스케줄러 시작");
		
		String path = this.getClass().getResource("").getPath();
		
		int index = path.indexOf("WEB-INF");
		//System.out.println(path);
		
		String realpath = path.substring(0, index);
		/*String path = request.getServletContext().getRealPath("/");*/
		
		//System.out.println(realpath);
		
		String exePath = realpath + "\\resources\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless"); // 창 없는 옵션
		options.addArguments("--hide-scrollbars"); // 스크롤바 없애는 옵션
		options.addArguments("window-size=1080x1080"); // 화면 크기 옵션
		options.addArguments("disable-gpu"); // 성능
		
		File forder = new File(realpath + "\\images\\homepage");
		if(!forder.exists()) {
			forder.mkdir();
		}
		
		List<A_BookDTO> list = a_book_service.getBooks();
		for(A_BookDTO book : list) {
			WebDriver driver = new ChromeDriver(options);
			driver.get(book.getUrl());
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			try {
				FileUtils.copyFile(scrFile, new File(realpath + "\\images\\homepage\\" + book.getAbid() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver.quit();
		}
		
		System.out.println("스케줄러 끝");
	}
}
