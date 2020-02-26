package com.jang.bbs.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jang.bbs.model.AttFileVO;
import com.jang.bbs.model.BoardLikeVO;
import com.jang.bbs.model.BoardVO;
import com.jang.bbs.model.BoardViewVO;
import com.jang.bbs.model.ReplyLikeVO;
import com.jang.bbs.model.ReplyVO;
import com.jang.bbs.model.SearchVO;

@Repository(value = "boardMapper") // @Mapper
public interface BoardMapper {

	List<BoardVO> getBoardList(SearchVO searchVO); // 글 목록

	BoardVO getArticle(int bno); // 글조회

	int writeArticle(BoardVO board); // 글저장

	int updateArticle(BoardVO board); // 글수정

	void deleteArticle(int bno); // 글삭제

	int getTotalRow(SearchVO searchVO); // 전체 글수 조회

	int incrementViewCnt(int bno); // 조회수 증가

	int incrementGoodCnt(int bno); // 좋아요 증가

	int incrementReplyCnt(int bno); // 댓글수 증가

	List<ReplyVO> getReplyList(int bno); // 댓글 목록 조회

	ReplyVO getReply(int rno); // 댓글 조회

	int writeReply(ReplyVO reply); // 댓글 저장

	int updateReply(ReplyVO reply); // 댓글 수정

	void deleteReply(int rno); // 댓글 삭제

	void deleteReplyBybno(int bno); // 원글에 소속된 댓글 전체 삭제

	int incReplyGoodCnt(int rno); // 댓글 좋아요 증가
	// int incrementReMultiCnt(int bno);

	List<AttFileVO> getFileList(int bno); // 첨부파일 목록 읽기

	String getFileName(int fno); // 파일 이름 조회

	int insertFile(AttFileVO file); // 파일 저장

	void deleteFile(int fno); // 첨부 파일 삭제

	void deleteFileBybno(int bno); // 원본 소속된 첨부파일 전체 삭제

	int addBoardLike(BoardLikeVO boardLike); // 원본글 추천 저장

	int getBoardLike(BoardLikeVO boardLike); // 원본글 추천 확인

	int addReplyLike(ReplyLikeVO replyLike); // 댓글 추천 기록 저장

	int getReplyLike(ReplyLikeVO replyLike); // 댓글 추천 기록 확인
	
	int addBoardView(BoardViewVO boardView); // 원본글 추천 기록 저장
	
	int getBoardView(BoardViewVO boardView); // 원본글 추천 기록 확인
}
