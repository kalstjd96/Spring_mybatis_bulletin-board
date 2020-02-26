package com.jang.bbs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jang.bbs.mapper.BoardMapper;
import com.jang.bbs.model.AttFileVO;
import com.jang.bbs.model.BoardLikeVO;
import com.jang.bbs.model.BoardVO;
import com.jang.bbs.model.BoardViewVO;
import com.jang.bbs.model.ReplyLikeVO;
import com.jang.bbs.model.ReplyVO;
import com.jang.bbs.model.SearchVO;
import com.jang.bbs.utils.PageHelper;

@Service(value = "boardService") // �뚢뫂�뱜嚥▲끇�쑎, 占쎄퐣�뜮袁⑸뮞, �뵳�뗫７筌욑옙占쎄숲�뵳占�, 筌띾벏�쓠 占쎈뮉 �뚢뫂�믭옙�뵠占쎄섐 占쎈툧占쎈퓠 揶쏆빘猿쒐몴占� 筌띾슢諭억옙堉� 占쎄퐫占쎈선占쎌뵬
public class BoardService {

	@Resource(name = "boardMapper") // autowired / �뚢뫂�믭옙�뵠占쎄섐 占쎈툧占쎈퓠 占쎌뿳占쎈뮉 野껉퍔�뱽 占쎈뼄占쎌벉餓κ쑴肉� �댆�눖沅∽옙�긾.
	private BoardMapper boardMapper;

	PageHelper pageHelper = new PageHelper();

	public StringBuffer getPageUrl(SearchVO searchVO) {
		int totalRow = boardMapper.getTotalRow(searchVO);
		//전체 글수를 조회하는 코드
		

		return pageHelper.getPageUrl(searchVO.getPage(), totalRow);
	}

	public List<BoardVO> getBoardList(SearchVO searchVO) {
		//BoardVO모델 정보가 담긴 List에 SearchVO정보가 넣어진 곳에서
		int currentPage = searchVO.getPage();
		//SearchVO 모델안의 page를 currentPage에 넣는다
		int startRow = (currentPage - 1) * this.pageHelper.getPageSize() + 1;
		//이는 								한 페이지당 10개씩 목록을 나타내게하기 위해 
		//해당 페이지의 첫번째 행의번호를 출력하기 위함이다.
		int endRow = currentPage * this.pageHelper.getPageSize();

		searchVO.setStartRow(startRow);
		searchVO.setEndRow(endRow);

		return this.boardMapper.getBoardList(searchVO);
	}

	public BoardVO getArticle(int bno) {
		return this.boardMapper.getArticle(bno);
	}

	public int writeArticle(BoardVO board) {
		return this.boardMapper.writeArticle(board);
	}

	public int updateArticle(BoardVO board) {
		return this.boardMapper.updateArticle(board);
	}

	public void deleteArticle(int bno) {
		this.boardMapper.deleteArticle(bno);
		return;
	}

	public int getTotalArticle(SearchVO searchVO) {
		return this.boardMapper.getTotalRow(searchVO);
	}

	public int incrementViewCnt(int bno) {
		return this.boardMapper.incrementViewCnt(bno);
	}

	public int incrementGoodCnt(int bno) {
		return this.boardMapper.incrementGoodCnt(bno);
	}

	public int incrementReplyCnt(int bno) {
		return this.boardMapper.incrementReplyCnt(bno);
	}

	public List<ReplyVO> getReplyList(int bno) {
		return this.boardMapper.getReplyList(bno);
	}

	public ReplyVO getReply(int rno) {
		return this.boardMapper.getReply(rno);
	}

	public int writeReply(ReplyVO reply) {
		int bno = reply.getBno();
		this.boardMapper.incrementReplyCnt(bno);

		return this.boardMapper.writeReply(reply);
	}

	public int updateReply(ReplyVO reply) {
		return this.boardMapper.updateReply(reply);
	}

	public void deleteReply(int rno) {
		this.boardMapper.deleteReply(rno);
		return;
	}

	public void deleteReplyBybno(int bno) {
		this.boardMapper.deleteReplyBybno(bno);
		return;
	}

	public int incReplyGoodCnt(int rno) {
		return this.boardMapper.incReplyGoodCnt(rno);
	}

	public List<AttFileVO> getFileList(int bno) {
		return this.boardMapper.getFileList(bno);
	}

	public String getFileName(int fno) {
		return this.boardMapper.getFileName(fno);
	}

	public int insertFile(AttFileVO file) {
		return this.boardMapper.insertFile(file);
	}

	public void deleteFile(int fno) {
		this.boardMapper.deleteFile(fno);
		return;
	}

	public void deleteFileBybno(int bno) {
		this.boardMapper.deleteFileBybno(bno);
		return;
	}

	public int addBoardLike(BoardLikeVO boardLike) {
		return this.boardMapper.addBoardLike(boardLike);
	}

	public int getBoardLike(BoardLikeVO boardLike) {
		return this.boardMapper.getBoardLike(boardLike);
	}

	public int addReplyLike(ReplyLikeVO replyLike) {
		return this.boardMapper.addReplyLike(replyLike);
	}

	public int getReplyLike(ReplyLikeVO replyLike) {
		return this.boardMapper.getReplyLike(replyLike);
	}

	public int addBoardView(BoardViewVO boardView) { // userid-湲�踰덊샇 議고쉶 湲곕줉 異붽�
		return this.boardMapper.addBoardView(boardView);
	}

	public int increaseViewCnt(int bno) { // 湲�踰덊샇 議고쉶�닔 利앷�
		return this.boardMapper.incrementViewCnt(bno);
	}

	public int getBoardView(BoardViewVO boardView) { // userid-湲�踰덊샇 議고쉶 湲곕줉 �씫湲�
		return this.boardMapper.getBoardView(boardView);
	}
}
