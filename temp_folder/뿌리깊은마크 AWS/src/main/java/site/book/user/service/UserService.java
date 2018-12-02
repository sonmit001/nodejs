package site.book.user.service;

import java.io.File;
/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준, 김태웅
 */
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.book.main.handler.RollinMailHandler;
import site.book.main.handler.RollinTempKey;
import site.book.team.dao.G_AlarmDAO;
import site.book.team.dao.G_BookDAO;
import site.book.team.dao.G_MemberDAO;
import site.book.user.dao.UserDAO;
import site.book.user.dto.EmailAuthDTO;
import site.book.user.dto.UserDTO;

@Service
public class UserService {
	
	// 변수 Start
	
	// 태웅
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 희준
	@Autowired
	private SqlSession sqlsession;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	// 명수
	
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	// Roll in ID check
	public int checkUserID(String uid) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.checkUserID(uid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// Roll in Nickname check
	public int checkUserNickname(String nname) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.checkUserNickname(nname);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// Roll in New User
	public int rollinUser(UserDTO user) {
		int row = 0;
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		ObjectMapper oMapper = new ObjectMapper();
		
		try {
			Map<String, String> convert_user = oMapper.convertValue(user, Map.class);
			System.out.println(convert_user);
			row = userDAO.insertNewUser(convert_user);
			System.out.println("row 결과");
			System.out.println(row);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			row = -1;
		}
		
		return row;
	}
	
	// Send authcode to User's email
	// 인증번호 이메일로 보내기
	public int confirmEmail(EmailAuthDTO authcode) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		String key = new RollinTempKey().getKey(10, false);
		int row = 0;
		System.out.println("userservice 회원가입 이메일 들어오는거 확인");
		System.out.println(authcode.getUid());
		
		// Auth Mail Form $ Send email 
		try {
			RollinMailHandler sendMail = new RollinMailHandler(mailSender);
			
			String templateLocation = "userAuthentication.vm";
			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
			String dateTime = sdf.format(cal.getTime());
			//System.out.println("dateTime : " + dateTime);
			
			Map<String, Object> vmmodel = new HashMap<>();
			vmmodel.put("key", key);
			vmmodel.put("dateTime", dateTime);
			
			String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
															templateLocation, "UTF-8", vmmodel);
			
			sendMail.setSubject("[뿌리깊은마크 회원가입 이메일 인증]");
			sendMail.setText(content);
			sendMail.setFrom("bitcamp104@gmail.com", "뿌리깊은마크 관리자");
			sendMail.setTo(authcode.getUid());
			sendMail.send();
			// save user's authcode
			authcode.setAuthcode(key);
			row = userDAO.insertAuthCode(authcode);
		}catch (Exception e) {
			e.getMessage();
		}
		
		return row;
	}
	
	// Check Authcode
	public int checkAuthcode(EmailAuthDTO authcode) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;

		try {
			row = userDAO.checkAuthCode(authcode);
		}catch (Exception e) {
			System.out.println("Authcode error");
		}
		
		return row;
	}
	
	// 한명의 회원정보 가져오기
	public UserDTO getMember(String uid) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		UserDTO editedUser = null;

		try {
			editedUser = userDAO.getUser(uid);
		}catch (Exception e) {
			System.out.println("Get User Info Error");
		}
		
		return editedUser;
	}
	
	// 한명의 회원정보 수정하기
	public String editMember(HttpServletRequest request, UserDTO user, MultipartFile file) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int result = 0;
		String changed_file_name = "";
		//System.out.println(user);
		//업로드한 파일이 있다면,
		if (file != null) {
			String filename = file.getOriginalFilename();
			String path = request.getServletContext().getRealPath("/");
			String[] temp = filename.split("\\.");
			if(temp.length > 1) {
				changed_file_name = user.getNname() + "." + temp[temp.length-1];
				user.setProfile(changed_file_name);
			
				String fpath = path + "images" + File.separator + "profile" + File.separator + changed_file_name;
	
				// 서버에 파일 업로드 (write)
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(fpath);
					fs.write(file.getBytes());
				} catch (Exception e) {
					System.out.println("이미지 쓰기 실패");
				} finally {
					try {
						fs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 파일 쓰기 완료 후, 유저 프로필 수정
		try {
			System.out.println(user);
			result = userDAO.editUser(user);
		}catch (Exception e) {
			System.out.println("Edit User Info Error");
		}
				
		return changed_file_name;
	}
	
	// 회원 탈퇴 서비스
	public int deleteMember(String uid) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int result = 0;

		try {
			result = userDAO.deleteUser(uid);
		}catch (Exception e) {
			System.out.println("Get User Info Error");
		}
		
		return result;
	}
	
	// 비밀번호 찾기 서비스
	public int findUserPwd(UserDTO user) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int result = 0;
		String pwd = new RollinTempKey().getKey(15, false).toLowerCase();
		try {
			// 회원이라면,
			if(userDAO.getUser(user.getUid()) != null) {
				// Send email with new temp password
				user.setPwd(this.bCryptPasswordEncoder.encode(pwd));
				result = userDAO.updatePwd(user);
				
				RollinMailHandler sendMail = new RollinMailHandler(mailSender);
				
				String templateLocation = "temporaryPassword.vm";
				
				Map<String, Object> vmmodel = new HashMap<>();
				vmmodel.put("pwd", pwd);
				
				String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
																templateLocation, "UTF-8", vmmodel);
				
				sendMail.setSubject("[뿌리깊은마크 임시비밀번호 발급]");
				sendMail.setText(content);
				sendMail.setFrom("bitcamp104@gmail.com", "뿌리깊은마크 관리자");
				sendMail.setTo(user.getUid());
				sendMail.send();
			}
		}catch (Exception e) {
			
		}
		
		return result;
	}
	
	// 회원(이메일) 확인 서비스
	public int confirmUser(EmailAuthDTO user) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		String key = new RollinTempKey().getKey(10, false);
		int result = 0;
		
		try {
			// 회원이라면,
			String uid = user.getUid();
			UserDTO member =  userDAO.getUser(uid);
			System.out.println(member);
			if(member != null) {
				// Send email Authcode
				
				RollinMailHandler sendMail = new RollinMailHandler(mailSender);
				
				String templateLocation = "passwordAuthentication.vm";
				
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
				String dateTime = sdf.format(cal.getTime());
				//System.out.println("dateTime : " + dateTime);
				
				Map<String, Object> vmmodel = new HashMap<>();
				vmmodel.put("key", key);
				vmmodel.put("dateTime", dateTime);
				
				String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
																templateLocation, "UTF-8", vmmodel);
				
				sendMail.setSubject("[뿌리깊은마크 비밀번호 찾기 이메일 인증]");
				sendMail.setText(content);
				sendMail.setFrom("bitcamp104@gmail.com", "뿌리깊은마크 관리자");
				sendMail.setTo(user.getUid());
				sendMail.send();
				
				// save user's authcode
				user.setAuthcode(key);
				result = userDAO.insertAuthCode(user);
				//System.out.println("전송 & insert 완료");
			}
		}catch (Exception e) {
			
		}
		
		return result;
	}
	
	// 전 회원의 닉네임 가져오기 기능
	public List<String> getAllUserNname(String nname) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		List<String> result = null;
		
		try {
			result = userDAO.getAllUserNname(nname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 희준
	
	// 전체 회원수 가져오기
	public int getAllUser() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.allUser();
		} catch (ClassNotFoundException | SQLException e) {
			//e.printStackTrace();
		}
		
		return row;
	}
	
	// 신규 가입자수 가져오기
	public List<HashMap<String, String>> getNewUser() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		List<HashMap<String, String>> list = null;
		
		try {
			list = userDAO.newUser();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 블랙리스트 추가하기
	@Transactional
	public int blacklist(String uid) {
		UserDAO userdao = sqlsession.getMapper(UserDAO.class);
		G_BookDAO gbookdao = sqlsession.getMapper(G_BookDAO.class);
		G_MemberDAO gmemberdao = sqlsession.getMapper(G_MemberDAO.class);
		G_AlarmDAO galarmdao = sqlsession.getMapper(G_AlarmDAO.class);

		int row = 0;
		
		try {
			userdao.blacklist(uid);
			userdao.deleteUserBook(uid);
			gbookdao.deleteGroupBook(uid);
			gmemberdao.leaveAllGroup(uid);
			galarmdao.deleteAllGroupAlarm(uid);
			row = 1;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 블랙리스트가 아닌 회원 리스트 가져오기 
	public List<UserDTO> getUserList() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		List<UserDTO> list = null;
		
		try {
			list = userDAO.getUserList();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	// 명수
	
	
	// 함수 End
}
