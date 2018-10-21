package com.robo.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scripting.support.StandardScriptEvaluator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.robo.web.service.Account;
import com.robo.web.service.Basic;
import com.robo.web.service.Board;
import com.robo.web.utils.EncryptionUtil;
import com.robo.web.utils.comm;
import com.robo.web.vo.BoardInfo;
import com.robo.web.vo.FileInfo;
import com.robo.web.vo.ReplyTextInfo;
import com.robo.web.vo.RsaVo;
import com.robo.web.vo.UserInfo;

import net.sf.json.JSONObject;


@Controller
public class MainController {
	
	String savePath_temp = "";
	String savePath ="";
	private static final Logger logger = LoggerFactory.getLogger("catalina");
	
	@Autowired
	Basic bser;
	
	@Autowired
	Account acctser;
	
	@Autowired
	Board bbser;
	
	@RequestMapping(value="editBoard.do",method = RequestMethod.GET)
	public String editBoard(HttpServletRequest req, HttpServletResponse res) {
		int mtext_id = Integer.parseInt(req.getParameter("mtext_id"));
		BoardInfo boardinfo = new BoardInfo();
		boardinfo.setMtext_id(mtext_id);
		BoardInfo binfo = bbser.getBoardView(boardinfo);
		req.setAttribute("bInfo", binfo);
		return "board/editBoard";
	}
	@RequestMapping(value="editBoard.do" , method = RequestMethod.POST)
	public String editBoardOk(HttpServletRequest req, HttpServletResponse res,BoardInfo bInfo) {
		return null;
	}
	
	@RequestMapping(value="writeReply.do")
	public String writeReply(HttpServletRequest req, HttpServletResponse res, ReplyTextInfo rtextinfo) {
		System.out.println(rtextinfo);
		UserInfo uinfo = comm.getUser(req);
		if(uinfo != null) {
			rtextinfo.setAccnt_id(uinfo.getAccnt_id());
			rtextinfo.setNickname(uinfo.getNickname());
			bbser.insertComment(rtextinfo);
		}else {
			req.setAttribute("url", "logIn.do");
			req.setAttribute("msg", "로그인 후 이용해 주세요");
			return "redirect";
		}
		
		return "redirect:goBoardView.do?mtext_id=" + rtextinfo.getMtext_id();
	}
	
	@RequestMapping(value="getfile.do")
	public void getfile(HttpServletRequest req, HttpServletResponse res) {
		String attach_id = req.getParameter("attach_id");
		FileInfo finfo = new FileInfo();
		finfo.setAttach_id(attach_id);
		FileInfo fileinfo = bbser.getFileInfo(finfo);
		//"C:/bitcamp104/Spring/Lab/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/web/img_upload";
		String path = fileinfo.getPath();
		String filename = fileinfo.getNew_name();
		File file = new File(path + filename);
		System.out.println(path);
		InputStream is = null;
		
		try {
			if (file == null || !file.exists() || file.length() <= 0 || file.isDirectory()) {
			      throw new IOException("파일 객체가 Null 혹은 존재하지 않거나 길이가 0, 혹은 파일이 아닌 디렉토리이다.");
			    }
			is = new FileInputStream(file);
			res.setContentType("application/force-download");
			res.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"");
			FileCopyUtils.copy(is,res.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "goBoard.do")
	public String goBoard(HttpServletRequest req, HttpServletResponse res){
		logger.info("goBoard.do");
		ArrayList<Object> list = bbser.getBoardList("M");
		int count = list.size();
		for(int i =0; i<list.size();i++){
			((BoardInfo)list.get(i)).setBoard_id(count-i);
		}
		
		req.setAttribute("list", list);
		return "board/list";
	}
	@RequestMapping(value = "goBoardView.do")
	public String goBoardView(HttpServletRequest req, HttpServletResponse res,BoardInfo bInfo){
		//결과 값 : [{"mtext_id":"2"},{"mtext_id":"3"}]
		System.out.println(bInfo);
		
		if(bInfo.getJson() != null){
			List<String> list = new ArrayList<String>();
			for(int i =0; i<bInfo.getJson().length;i++){
				System.out.println(bInfo.getJson()[i]);
				list.add(bInfo.getJson()[i]);
			}
			req.getSession().setAttribute("json", list);
			System.out.println(list);
		}
		//view 올리기
		bbser.updateViewCnt(bInfo);
		//글 가져오기
		BoardInfo binfo = bbser.getBoardView(bInfo);
		binfo.setMtext_id(bInfo.getMtext_id());
		//댓글 가져오기
		ArrayList<Object> rtextinfo = bbser.getCommentList(bInfo);
		//파일 정보 가져오기
		FileInfo fileinfo = new FileInfo();
		fileinfo.setMtext_id(bInfo.getMtext_id());
		ArrayList<Object> files = bbser.getFileList(fileinfo);
		//글 가져오기
	
		//현재 순서 넣기
		binfo.setArr_num(bInfo.getArr_num());
		req.setAttribute("binfo", binfo);
		req.setAttribute("rtextinfo", rtextinfo);
		req.setAttribute("finfo", files);
		return "board/view";
	}
	
	@RequestMapping(value = "writeBoard.do")
	public String writeBoard(HttpServletRequest req, HttpServletResponse res){
		logger.info("writeBoard");
		UserInfo ui = comm.getUser(req);
		if(ui == null){
			req.setAttribute("url", "logIn.do");
			return "redirect";
		}
		System.out.println(ui);
		req.setAttribute("bInfo", ui);
		
		return "board/write";
	}
	
	@RequestMapping(value = "writeok.do", method = RequestMethod.GET)
	public String writeok(HttpServletRequest req, HttpServletResponse res,BoardInfo bInfo) {
		//insert 후 id 값 가져오기
		System.out.println(bInfo);
		int mtext_id = bbser.insertBoard(bInfo);
		//system.out.println(mtext_id);
		List<FileInfo> list = new ArrayList<FileInfo>();
		Map<String, Object> map = new HashMap<String, Object>();
		if(bInfo.getAttachfile() != null) {
			for(String name : bInfo.getAttachfile()){
				System.out.println(name);
				File file = new File(savePath_temp + name);
				File filenew = new File(savePath + name);
				if(file.exists()) file.renameTo(filenew);
				String ori_name = name.split("_")[1];
				FileInfo finfo = new FileInfo();
				finfo.setPath(savePath);
				finfo.setOrg_name(ori_name);
				finfo.setNew_name(name);
				finfo.setMtext_id(mtext_id);
				System.out.println("파일 정보 insert 한 거");
				System.out.println(finfo);
				
				list.add(finfo);
			}
		}
		map.put("list", list);
		bbser.insertFileInfo(map);
		System.out.println(bInfo.getAttachfile());
		return "redirect:goBoardView.do?mtext_id=" + mtext_id;
	}

	@RequestMapping(value = "insertFile.do", method = RequestMethod.POST)
	public void insertFile(HttpServletRequest req, HttpServletResponse res,MultipartHttpServletRequest request) throws IOException{
		//temp에 insert 한다.
		System.out.println("upload file inserting ");
		Iterator<String> itr = request.getFileNames();
		System.out.println("아래 itr");
		System.out.println(itr);
		MultipartFile mpf = null;
		savePath_temp = request.getServletContext().getRealPath("temp_file");//임시 폴더
		savePath =  request.getServletContext().getRealPath("img_upload");
		File filedir = new File(savePath_temp);
		if( !filedir.exists() ) {
			filedir.mkdirs();
		}
		List<FileInfo> list = new ArrayList<FileInfo>();
		Map<String, Object> mapresult = new HashMap<String,Object>();
		while(itr.hasNext()){
			try {
				 FileInfo finfo = new FileInfo();
				 mpf = request.getFile(itr.next()); 
				 String original_name = mpf.getOriginalFilename();
				 String realName ="/"+ UUID.randomUUID().toString() + "_"+ original_name;//실 저장 이름
				 File file = new File(savePath_temp, realName);
				 mpf.transferTo(file);
				// finfo.setOrg_name(mpf.getOriginalFilename());
				// finfo.setNew_name(realName);
				 list.add(finfo);
				 mapresult.put("org_name", original_name);
				 mapresult.put("realname", realName);
				 mapresult.put("realpath", savePath_temp + realName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
		}
		JSONObject jso = JSONObject.fromObject(mapresult);
		res.getWriter().println(jso);
	}
	
/*	@RequestMapping(value = "writeok.do")
public String writeok(HttpServletRequest req, HttpServletResponse res,MultipartHttpServletRequest request,BoardInfo bInfo){
	//insert 후 id 값 가져오기
	System.out.println(bInfo);
	int mtext_id = bbser.insertBoard(bInfo);
	System.out.println(mtext_id);
	
	Iterator<String> itr = request.getFileNames();
	System.out.println(itr);
	MultipartFile mpf = null;
	String savePath = request.getServletContext().getRealPath("img_upload");
	List<FileInfo> list = new ArrayList<FileInfo>();
	Map<String, Object> map = new HashMap<String, Object>();
	while(itr.hasNext()){
		System.out.println("몇번이나 도니");
		 try {
			 FileInfo finfo = new FileInfo();
			 mpf = request.getFile(itr.next()); 
			 String original_name = mpf.getOriginalFilename();
			 String realName ="/"+ UUID.randomUUID().toString() + "_"+ original_name;
			 File file = new File(savePath, realName);
			 mpf.transferTo(file);
			 finfo.setPath(savePath);
			 finfo.setOrg_name(mpf.getOriginalFilename());
			 finfo.setNew_name(realName);
			 finfo.setMtext_id(mtext_id);
			 list.add(finfo);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}
	map.put("list", list); 
	bbser.insertFileInfo(map);
	System.out.println("testing"); 
	return "redirect:goBoardView.do&mtext_id=" + mtext_id;
}

 * 
 * */
	//메인 화면
	@RequestMapping(value = "home.do", method = RequestMethod.GET)
	public String home(HttpServletRequest req, HttpServletResponse res) {
		String day = comm.currentDate("yyyyMMdd");
		String time = comm.currentDate("hhmm");
		String dayweek = comm.currentDate("E");
		req.setAttribute("date", day);
		req.setAttribute("time", time);
		req.setAttribute("dayweek", dayweek);
		List<String> k = new ArrayList<>(); 
		for(int i = 0 ; i <5;){
			k.add(String.valueOf(i));
			System.out.println(i);
			break;
		}
		req.setAttribute("result", k);
		return "home";
	}
	//회원 리스트 가져오기
	@RequestMapping(value = "getMemberList.do")
	public String getMemberList(HttpServletRequest req, HttpServletResponse res){
		res.setCharacterEncoding("UTF-8");
		String name = "김선진";
		List<HashMap<String, String>> list = bser.getMemberList(name);
		req.setAttribute("list", list);
		
		return "member_list";
	}
	//슬라이더로 넘어가기
	@RequestMapping(value = "gounslider.do")
	public String goMemberList(HttpServletRequest req, HttpServletResponse res){
		return "unslider";
	}
	//회원 가입 페이지
	@RequestMapping(value = "signUp.do", method = RequestMethod.GET)
	public String signUp(HttpServletRequest req, HttpServletResponse res){
		return "signUp";
	}
	
	@RequestMapping(value = "signUp.do", method = RequestMethod.POST)
	public String joinMember(HttpServletRequest req, HttpServletResponse res, UserInfo userinfo){
		try {
			System.out.println("암호화 확인");
			System.out.println(userinfo);
			userinfo.transJoinMember(req);
			int accnt_id = acctser.joinMember(userinfo);
				if(accnt_id>0){
					UserInfo ui = acctser.getUserInfo(accnt_id);
					comm.loginProcess(req, res, ui);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	//아이디 중복 확인
	@RequestMapping(value = "chkId.do")
	public void chkId(HttpServletRequest req, HttpServletResponse res, String chkId) throws IOException{
		res.setCharacterEncoding("UTF-8");
		int result = acctser.chkId(chkId);
		res.getWriter().println(result);
	}
	//닉네임 중복 확인
	@RequestMapping(value = "chkNicName.do")
	public void chkNicName(HttpServletRequest req, HttpServletResponse res, String nickname) throws IOException{
		res.setCharacterEncoding("UTF-8");
		int result = acctser.chkNicName(nickname);
		res.getWriter().println(result);
	}
	
	@RequestMapping(value = "logIn.do", method= RequestMethod.GET)
	public String logInPage(HttpServletRequest req, HttpServletResponse res, UserInfo userinfo){
		return "logIn";
	}
	
	@RequestMapping(value = "logIn.do", method= RequestMethod.POST)
	public String logIn(HttpServletRequest req, HttpServletResponse res, UserInfo userinfo){
		System.out.println("login post");
		String url = "redirect";
		try {
			userinfo.transPasswd(req);
			System.out.println(userinfo);
			UserInfo ui = acctser.loginMember(userinfo);
			System.out.println(ui);
			if(ui == null){
				req.setAttribute("result", "f");
				req.setAttribute("msg", "아이디 혹은 비밀번호가 틀리셨습니다.");
				req.setAttribute("url", "logIn.do");
			}else{
				comm.loginProcess(req, res, ui);
				url = "redirect:home.do";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	@RequestMapping(value="logOut.do")
	public String logOut(HttpServletRequest req, HttpServletResponse res){
		comm.logoutProcess(req, res, "ui");
		
		return "home";
	}
	//rsa 공개키 만들기
	@RequestMapping(value = "getRsaKey.do")
	public void getRsaKey(HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setCharacterEncoding("UTF-8");

		JSONObject json = new JSONObject();
		RsaVo rVo = EncryptionUtil.getRsaKey();
		
		req.getSession().setAttribute("privateKey", rVo.getPrivateKey());
		json.put("publicKeyExponent", rVo.getPublicKeyExponent());
		json.put("publicKeyModulus", rVo.getPublicKeyModulus());
		
		res.getWriter().println(json);
	}
	@RequestMapping(value = "/multiplePhotoUpload.do")
	public void multiplePhotoUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("multiplePhotoUpload");
		String savePath = request.getServletContext().getRealPath("img_upload");
		String original_name = request.getHeader("file-name");
		//String ext = original_name.substring(original_name.lastIndexOf(".")+1).toLowerCase();
		String uploadPath = request.getServletContext().getRealPath("img_upload");
		File file = new File(uploadPath);
		if( !file.exists() ) {
			file.mkdirs();
		}
		String realName ="/"+ UUID.randomUUID().toString() + "_"+ original_name;
		
		InputStream is = request.getInputStream();
		OutputStream os = new FileOutputStream(uploadPath + realName);
		int numRead;
		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
		if( is != null ) {
			while( (numRead = is.read(b,0,b.length)) != -1 ) {
				os.write(b,0,numRead);
			}
			is.close();
		}
		os.flush();
		os.close();
		System.out.println(os);
		String sFileInfo = "";
		sFileInfo += "&bNewLine=true";
		sFileInfo += "&sFileName="+original_name;
		sFileInfo += "&sFileURL="+savePath +realName;
		System.out.println(sFileInfo);
		response.getWriter().println(sFileInfo);
	}
	
}
