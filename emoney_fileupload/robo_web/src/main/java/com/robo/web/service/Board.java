package com.robo.web.service;

import java.util.ArrayList;
import java.util.Map;

import com.robo.web.vo.BoardInfo;
import com.robo.web.vo.FileInfo;
import com.robo.web.vo.ReplyTextInfo;

public interface Board {
	
	public ArrayList<Object> getBoardList(String type);
	public BoardInfo getBoardView(BoardInfo bInfo);
	public int insertBoard(BoardInfo bInfo);
	public int updateBoard(BoardInfo bInfo);
	public int deleteBoard(BoardInfo bInfo);
	public ArrayList<Object> getCommentList(BoardInfo bInfo);
	public int insertComment(ReplyTextInfo rTInfo);
	public int deleteComment(ReplyTextInfo rTInfo);
	public FileInfo getFileInfo(FileInfo fInfo);
	public int updateViewCnt(BoardInfo bInfo);
	public int insertFileInfo(Map<String, Object> map);
	public ArrayList<Object> getFileList(FileInfo fInfo);
	/*insert file 추가ArrayList<Object>*/

}
