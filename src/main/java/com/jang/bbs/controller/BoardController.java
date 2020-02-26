package com.jang.bbs.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jang.bbs.model.AttFileVO;
import com.jang.bbs.model.BoardLikeVO;
import com.jang.bbs.model.BoardVO;
import com.jang.bbs.model.BoardViewVO;
import com.jang.bbs.model.ReplyLikeVO;
import com.jang.bbs.model.ReplyVO;
import com.jang.bbs.model.SearchVO;
import com.jang.bbs.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	/*
	 * @Resource(name = "boardService") // 
	 */ @Autowired //의존관계 주입
	 	private BoardService boardService; //의존관계 설정 

	// file upload path
	private String uploadPath = "C:\\upload\\";

	
	//리스트를 보는 페이지 
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//get방식(URL형식에 /list라는 값이 입력이 되었을 때 
	public String listPage(@ModelAttribute("searchVO") SearchVO searchVO, Model model)
	//						이는 "searchVO라는 이름으로 지정된 이름으로 모델 정보에 담아진다.			
	//						어떤 것이? SearVO의 값들이 이를 통해서 View 부분에 사용하기 위해 
			throws Exception {
		// session.setAttribute("userId", "TestId"); // login 媛쒕컻�셿猷� �썑 �궘�젣
		//session.setAttribute("userName", "Tester"); // login 한 사용자의 이름을 저장

		List<BoardVO> blist = boardService.getBoardList(searchVO);
		//List 생성 BoardVO 형식의 리스트를 생성 
		model.addAttribute("boardList", blist);
		
		StringBuffer pageUrl = boardService.getPageUrl(searchVO);
		//StringBuffer는 프로그램 내에서 변하는 문자열을 나타낼때 주로 사용하는 코드
		//현재 출력되는 페이지의 목록을 출력하는데 start와 end page까지의 목록을 출력해주기 위함 
		model.addAttribute("pageHttp", pageUrl);

		model.addAttribute("searchVO", searchVO);

		return "board/list";
	}

	@RequestMapping(value = "/write.do", method = RequestMethod.GET)
	public String boardWrite() {
		return "/board/write";
	}

	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String boardWriteProc(@ModelAttribute("Board") BoardVO board, MultipartHttpServletRequest request) {

		// �깉 湲����옣
		String content = board.getContent().replaceAll("\r\n", "<br />"); // java�깉以� 肄붾뱶 HTML以꾨컮袁멸린濡�
		content = content.replaceAll("<", "&lt;");
		content = content.replaceAll(">", "&gt;");
		content = content.replaceAll("&", "&amp;");
		content = content.replaceAll("\"", "&quot;");

		board.setContent(content);
		boardService.writeArticle(board);
		int bno = board.getBno(); // ���옣�떆 �깮�꽦�븳 �깉湲�踰덊샇 xml �뙆�씪insert �뿉�꽌 keyProperty="bno" �뿉 �쓽�빐�꽌 �꽕�젙�맖

		// 泥⑤� �뙆�씪 ���옣
		AttFileVO file = new AttFileVO();
		String uploadPath = "C:\\upload\\";
		List<MultipartFile> fileList = request.getFiles("file");

		for (MultipartFile mf : fileList) {
			if (!mf.isEmpty()) {
				String originFileName = mf.getOriginalFilename(); // �썝蹂� �뙆�씪 紐�
				long fileSize = mf.getSize(); // �뙆�씪 �궗�씠利�

				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);

				file.setBno(bno);
				file.setOfilename(originFileName);
				file.setSfilename(originFileName);
				file.setFilesize(fileSize);

				boardService.insertFile(file); // �뀒�씠釉붿뿉 �뙆�씪 �젙蹂� ���옣

				String safeFile = uploadPath + originFileName; // �뵒�뒪�겕�뿉 �뙆�씪 ���옣

				try {
					mf.transferTo(new File(safeFile)); // �뵒�뒪�겕�뿉 �뙆�씪 ���옣
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:list.do";
	}

	@RequestMapping("/view")
	public String boardView(@RequestParam(value = "bno", required = false, defaultValue = "0") int bno,
			HttpSession session, Model model) throws Exception {

		String userId = (String) session.getAttribute("userId");

		BoardViewVO boardViewVO = new BoardViewVO(); // 湲�議고쉶 �씠�젰 �슜
		boardViewVO.setBno(bno);

		if (userId != null) { // �쉶�썝�씤 寃쎌슦
			boardViewVO.setUserId(userId);
			boardViewVO.setMem_yn('y'); // �쉶�썝

			if (boardService.getBoardView(boardViewVO) == 0) { // �빐�떦 踰덊샇 湲��쓣 �씫�� 湲곕줉�씠 �뾾�쑝硫�
				boardService.increaseViewCnt(bno); // 議고쉶�닔 媛깆떊
				boardService.addBoardView(boardViewVO); // �쉶�썝 議고쉶 �벑濡�
			}
		} else { // 鍮꾪쉶�썝�씤 寃쎌슦
			boardViewVO.setUserId(session.getId()); // �꽭�뀡id瑜� �쉶�썝 id濡� �벑濡�
			boardViewVO.setMem_yn('n'); // 鍮꾪쉶�썝

			if (boardService.getBoardView(boardViewVO) == 0) {
				{ // �빐�떦 踰덊샇 湲��쓣 �씫�� 湲곕줉�씠 �뾾�쑝硫�
					boardService.increaseViewCnt(bno); // 議고쉶�닔 媛깆떊
					boardService.addBoardView(boardViewVO); // 鍮꾪쉶�썝 議고쉶�닔 �벑濡�
				}
			}
		}
		BoardVO board = boardService.getArticle(bno); // get selected article model
		List<ReplyVO> reply = boardService.getReplyList(bno); // �떟蹂� 紐⑸줉 �씫�뼱 �삤湲� �� list
		List<AttFileVO> fileList = boardService.getFileList(bno); // 泥⑤��뙆�씪 紐⑸줉 �씫�뼱 �삤湲�-list

		model.addAttribute("board", board);
		model.addAttribute("replyList", reply);
		model.addAttribute("fileList", fileList);
		return "board/view";
	}

	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String boardModify(HttpServletRequest request, HttpSession session, Model model) {

		String userId = (String) session.getAttribute("userId");
		int bno = Integer.parseInt(request.getParameter("bno"));

		BoardVO board = boardService.getArticle(bno);

		// <br /> tag change to new line code
		String content = board.getContent().replaceAll("<br />", "\r\n");
		board.setContent(content);

		if (!userId.equals(board.getWriterId())) { // 鍮꾪쉶�썝 湲��닔�젙 遺덇�
			model.addAttribute("errCode", "1");
			model.addAttribute("bno", bno);
			return "redirect:view.do";
		} else {// �쉶�썝 湲��닔�젙
			List<AttFileVO> fileList = boardService.getFileList(bno); // 泥⑤��뙆�씪 �씫�뼱 �삤湲� - list

			model.addAttribute("board", board);
			model.addAttribute("fileList", fileList);
			return "/board/modify";
		}
	}

	@RequestMapping(value = "/modify.do", method = RequestMethod.POST) // 寃뚯떆�뙋 湲� �닔�젙
	public String boardModifyProc(@ModelAttribute("Board") BoardVO board, MultipartHttpServletRequest request,
			Model model) {

		String content = board.getContent().replaceAll("\r\n", "<br />"); // java �깉以� 肄붾뱶 HTML以꾨컮袁멸린濡�
		board.setContent(content);
		boardService.updateArticle(board);
		int bno = board.getBno();
		// 泥댄겕�맂 �뙆�씪�쓣 �뀒�씠釉붽낵 �뵒�뒪�겕�뿉�꽌 �궘�젣�븳�떎.
		String[] fileno = request.getParameterValues("fileno");
		if (fileno != null) {
			for (String fn : fileno) {
				int fno = Integer.parseInt(fn);
				String oFileName = boardService.getFileName(fno);
				String safeFile = uploadPath + oFileName;
				File removeFile = new File(safeFile);// remove disk uploaded file
				removeFile.delete();
				boardService.deleteFile(fno); // remove table uploaded file
			}
		}
		AttFileVO file = new AttFileVO();

		// �깉泥⑤� �뙆�씪 紐⑸줉 �씪�뼱�삤湲�
		List<MultipartFile> fileList = request.getFiles("file");
		for (MultipartFile mf : fileList) {
			if (!mf.isEmpty()) {
				String originFileName = mf.getOriginalFilename(); // �깉泥⑤��뙆�씪 �썝蹂� �뙆�씪 紐�
				long fileSize = mf.getSize(); // �뙆�씪 �궗�씠利�
				file.setBno(bno);
				file.setFilesize(fileSize);
				file.setOfilename(originFileName);
				file.setSfilename(originFileName);
				boardService.insertFile(file); // �뀒�씠釉붿뿉 �뙆�씪 ���옣
				String safeFile = uploadPath + originFileName;
				try {
					mf.transferTo(new File(safeFile)); // �뵒�뒪�겕�뿉 �뙆�씪 ���옣
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("bno", board.getBno());
		return "redirect:/board/view.do";
	}

	@RequestMapping("/delete.do")
	public String boardDelete(HttpServletRequest request, HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId"); // login 媛쒕컻�뮘 �궘�젣
		int bno = Integer.parseInt(request.getParameter("bno"));

		BoardVO board = boardService.getArticle(bno);

		String setView = "";
		if (userId.equals(board.getWriterId())) {
			// �떟湲� �궘�젣
			List<ReplyVO> reply = boardService.getReplyList(bno);
			if (reply.size() > 0) {
				boardService.deleteReplyBybno(bno);
			}
			// 泥⑤� �뙆�씪紐� �궘�젣, �떎�젣 �뙆�씪 �궘�젣
			List<AttFileVO> files = boardService.getFileList(bno);
			if (files.size() > 0) { // ���옣�맂 �떎�젣 �뙆�씪 �궘�젣
				for (AttFileVO filedel : files) {
					String f_stor_all = filedel.getOfilename();
					File f = new File(session.getServletContext().getRealPath("/") + f_stor_all);
					f.delete();
				}
				boardService.deleteFileBybno(bno); // �뀒�씠釉붿뿉�꽌 �빐�떦 踰덊샇 湲� 泥⑤� file �쟾泥� �궘�젣
			}
			// board �궘�젣
			boardService.deleteArticle(bno);
			setView = "redirect:list.do";
		} else {
			model.addAttribute("errCode", "1");// it's forbidden connection
			model.addAttribute("bno", bno);
			setView = "redirect:view.do";
		}
		return setView;
	}

	@RequestMapping("/recommend.do")
	public String updateRecommendCnt(HttpServletRequest request, HttpSession session, Model model) {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		String userId = (String) session.getAttribute("userId");

		if (userId == null) {
			model.addAttribute("bno", bno);
			model.addAttribute("errCode", "4");
			return "redirect:/board/view.do";
		}

		BoardLikeVO boardLike = new BoardLikeVO();
		boardLike.setBno(bno);
		boardLike.setUserId(userId);

		BoardVO board = boardService.getArticle(bno);
		
		if (board.getWriterId().equals(userId)) {
			model.addAttribute("errCode", "3"); // 蹂몄씤 湲��� 異붿쿇 遺덇�
		} else {
			if (boardService.getBoardLike(boardLike) == 0) { // �씠誘� 異붿쿇�븳 湲곕줉�씠 �뾾�쑝硫�
				boardService.incrementGoodCnt(bno);
				boardService.addBoardLike(boardLike); // 異붿쿇 湲곕줉 �벑濡�
			} else {
				model.addAttribute("errCode", "2"); // �씠誘� 異붿쿇�뻽�뜕 湲��씠硫� 異붿쿇 遺덇�
			}
		}
		model.addAttribute("bno", bno);
		return "redirect:/board/view.do";
	}

	@RequestMapping("/writeReply.do")
	public String replyWriteProc(@ModelAttribute("reply") ReplyVO reply, Model model) {

		if (reply.getContent().isEmpty()) {
			model.addAttribute("errCode", "1");
		} else {
			String content = reply.getContent().replaceAll("<", "&lt;");
			content = reply.getContent().replaceAll(">", "&gt;");
			content = reply.getContent().replaceAll("&", "&amp;");
			content = reply.getContent().replaceAll("\"", "&quot;");
			content = reply.getContent().replaceAll("\r\n", "<br />");

			reply.setContent(content);
			boardService.writeReply(reply);
			model.addAttribute("bno", reply.getBno());
		}

		return "redirect:view.do";
	}

	@RequestMapping("/deleteReply.do")
	public String commentDelete(HttpServletRequest request, HttpSession session, Model model) {

		int rno = Integer.parseInt(request.getParameter("rno"));
		int bno = Integer.parseInt(request.getParameter("bno"));

		String userId = (String) session.getAttribute("userId");
		ReplyVO reply = boardService.getReply(rno);

		if (!userId.equals(reply.getWriterId())) {
			model.addAttribute("errCode", "1");
		} else {
			boardService.deleteReply(rno);
		}

		model.addAttribute("bno", bno); // move back to the article

		return "redirect:view.do";
	}

	@RequestMapping("/recommandReply.do")
	public String RecommendRely(HttpServletRequest request, HttpSession session, Model model) {

		int bno = Integer.parseInt(request.getParameter("bno"));
		int rno = Integer.parseInt(request.getParameter("rno"));
		String userId = (String) session.getAttribute("userId");

		if (userId == null) {
			model.addAttribute("bno", bno);
			model.addAttribute("errCode", "4");
			return "redirect:/board/view.do";
		}

		ReplyLikeVO replyLike = new ReplyLikeVO();
		replyLike.setRno(rno);
		replyLike.setUserId(userId);

		BoardVO board = boardService.getArticle(bno);

		if (board.getWriterId().equals(userId)) {
			model.addAttribute("errCode", "3");
		} else {
			if (boardService.getReplyLike(replyLike) == 0) { // �씠誘� 異붿쿇�븳 湲곕줉�씠 �뾾�쑝硫�
				boardService.incReplyGoodCnt(rno);
				boardService.addReplyLike(replyLike); // 異붿쿇 湲곕줉 �벑濡�
			} else {
				model.addAttribute("errCode", "2"); // �씠誘� 異붿쿇�뻽�뜕 湲��씠硫� 異붿쿇 遺덇�
			}
		}
		model.addAttribute("bno", bno);
		return "redirect:/board/view.do";
	}

	@RequestMapping(value = "filedown.do")
	@ResponseBody
	public byte[] downProcess(HttpServletResponse response, @RequestParam String fileName) throws IOException {

		File file = new File("c:/upload/" + fileName);
		byte[] bytes = FileCopyUtils.copyToByteArray(file); // SPRING 5.0 �씠�긽

		String fn = new String(file.getName().getBytes(), "iso_8859_1");

		response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
		response.setContentLength(bytes.length);

		return bytes;
	}
}