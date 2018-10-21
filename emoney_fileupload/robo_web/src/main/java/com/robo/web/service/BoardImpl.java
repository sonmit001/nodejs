package com.robo.web.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robo.web.dao.BoardDaoMapper;
import com.robo.web.vo.BoardInfo;
import com.robo.web.vo.FileInfo;
import com.robo.web.vo.ReplyTextInfo;

@Service
public class BoardImpl implements Board{
	
	@Autowired
	private BoardDaoMapper bbdao;

	@Override
	public ArrayList<Object> getBoardList(String type) {
		return bbdao.getBoardList(type);
	}

	@Override
	public BoardInfo getBoardView(BoardInfo bInfo) {
		return bbdao.getBoardView(bInfo);
	}

	@Override
	public int insertBoard(BoardInfo bInfo) {
		int mtext_id = 0;
		
		mtext_id = bbdao.getMtext_id();
		if(mtext_id>0){
			bInfo.setMtext_id(mtext_id);
			bbdao.insertBoard(bInfo);
		}
		return mtext_id;
	}

	@Override
	public int updateBoard(BoardInfo bInfo) {
		int result = 0;
		if(bbdao.updateBoard(bInfo) ==1 ){
			result = 1;
		}
		return result;
	}

	@Override
	public int deleteBoard(BoardInfo bInfo) {
		return bbdao.deleteBoard(bInfo);
	}

	

	@Override
	public int insertComment(ReplyTextInfo rTInfo) {
		int result = 0;
		if(bbdao.insertComment(rTInfo) == 1){
			result = 1;
		}else{
			
		}
		return result;
	}

	@Override
	public int deleteComment(ReplyTextInfo rTInfo) {
		int result = 0;
		if(bbdao.deleteComment(rTInfo) == 1){
			result = 1;
		}
		return result;
	}

	@Override
	public FileInfo getFileInfo(FileInfo fInfo) {
		return bbdao.getFileInfo(fInfo);
	}

	@Override
	public int updateViewCnt(BoardInfo bInfo) {
		return bbdao.updateViewCnt(bInfo);
	}

	@Override
	public ArrayList<Object> getCommentList(BoardInfo bInfo) {
		return bbdao.getCommentList(bInfo);
	}

	@Override
	public int insertFileInfo(Map<String, Object> map) {
		return bbdao.insertFileInfo(map);
	}

	@Override
	public ArrayList<Object> getFileList(FileInfo fInfo) {
		return bbdao.getFileList(fInfo);
	}

	

}
