package com.robo.web.dao;

import java.util.ArrayList;
import java.util.Map;

import com.robo.web.vo.BoardInfo;
import com.robo.web.vo.FileInfo;
import com.robo.web.vo.ReplyTextInfo;

public interface BoardDaoMapper {
	//전체 게시판
	ArrayList<Object> getBoardList(String type);
	//게시판 상세보기
	BoardInfo getBoardView(BoardInfo bInfo);
	//seq 가져오기
	int getMtext_id();
	//게시판 글 쓰기
	void insertBoard(BoardInfo bInfo);
	//게시판 수정
	int updateBoard(BoardInfo bInfo);
	//게시글 삭제
	int deleteBoard(BoardInfo bInfo);
	//댓글 가져오기
	ArrayList<Object> getCommentList(BoardInfo bInfo);
	//댓글 달기
	int insertComment(ReplyTextInfo rTInfo);
	//댓글 삭제
	int deleteComment(ReplyTextInfo rTInfo);
	//첨부 파일 가져오기
	FileInfo getFileInfo(FileInfo fInfo);
	//조회수 올리기
	int updateViewCnt(BoardInfo bInfo);
	//insert file
	int insertFileInfo(Map<String, Object> map);
	//filelist
	ArrayList<Object> getFileList(FileInfo fileInfo);


}
