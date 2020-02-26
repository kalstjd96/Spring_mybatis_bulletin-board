package com.jang.bbs.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jang.bbs.model.UserVO;
import com.jang.bbs.service.LoginService;
import com.jang.bbs.utils.AES256Util;
import com.jang.bbs.utils.BCrypt;

@Controller
@RequestMapping("/member")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/login.do", method = RequestMethod.GET) // url占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈뼩占쎈섣占쎌굲筌욌ŀ�쐻�뜝占�.
	public String login() {
		return "/member/login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST) // form占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
																		// 占쎈쐻占쎈뼩占쎈섣占쎌굲鸚룸떱�쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
																		// post占쎈쐻占쎈짗占쎌굲 占쎈쐻占쎈뼎筌뤿슣�굲
	public String loginProc(@Valid UserVO userVO, BindingResult result, Model model, HttpSession session) {// valid
																											// 占쏙옙占쎈쐻占쎈쑟占쎄쉐占쎈쐻占쎈뼏占쎄땔占쎌굲

		if (result.hasFieldErrors("userId") || result.hasFieldErrors("passwd")) {
			model.addAllAttributes(result.getModel());// result.getModel占쎈쐻占쎈짗占쎌굲
														// 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈솭占쎈닰占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲
														// 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲占쎈쐻�뜝占� 占쎈쐻占쎈짗占쎌굲占쎈쐻占쎈짗占쎌굲.
			return "/member/login";
		}

		// UserVO loginUser = this.loginService.findUser(userVO);
		UserVO loginUser = this.loginService.getUser(userVO.getUserId());

		if (loginUser == null) {
			model.addAttribute("userId", "");
			model.addAttribute("errCode", 1); // not exist userId in database
			return "/member/login";
		} else if (BCrypt.checkpw(userVO.getPasswd(), loginUser.getPasswd())) { // �뙣�뒪�썙�뱶 �씪移�
			model.addAttribute("loginUser", loginUser);
			session.setAttribute("userId", loginUser.getUserId()); // �꽭�뀡�뿉 蹂��닔�벑濡�
			session.setAttribute("userName", loginUser.getName()); // �꽭�뀡�뿉 蹂��닔�벑濡�
			return "redirect:/board/list.do";
		} else {
			model.addAttribute("userId", "");
			model.addAttribute("errCode", 1);// �뙣�뒪�썙�뱶 遺덉씪移�

			return "/member/login";
		}
	}

	
	  @RequestMapping("/logout.do") public String logout(HttpSession session) {
	  session.invalidate(); 
	  return "redirect:/member/login.do"; }
	 

	/*
	 * @RequestMapping("/logout.do") public String logout(HttpSession session, Model
	 * model) { session.invalidate(); model.addAttribute("msg", "정상적으로 로그아웃!!!");
	 * 
	 * return "/member/logout"; // redirect 로 하게 되면 다음에 오는게 jsp파일명이 아닌 url로 전달한다 //
	 * 즉 view 이름이 아닌 직접 url을 입력한 꼴이 된다는 것이다. }
	 */

	@RequestMapping("/ajaxlogin.do")
	public String ajaxlogin() {
		return "/member/ajaxlogin";
	}

	@RequestMapping(value = "/ajaxlogin.do", method = RequestMethod.POST)
	public @ResponseBody String AjaxView(@Valid UserVO user, BindingResult bindingResult, HttpSession session) {
		//html body에 직접 보내는 것
		String err = null;
		if (bindingResult.hasFieldErrors("userId")) {
			err = "eid";
		} else if (bindingResult.hasFieldErrors("passwd")) {
			err = "epass";
		}

		Gson gson = new Gson();
		JsonObject object = new JsonObject();

		UserVO loginUser = this.loginService.getUser(user.getUserId());

		if (loginUser == null) {
			object.addProperty("id", "Null");
			object.addProperty("msg", "Fail");
			object.addProperty("err", err);

			String json = gson.toJson(object);
			return json.toString();
		} else {
			session.setAttribute("userId", loginUser.getUserId()); // �꽭�뀡�뿉 蹂��닔�벑濡�
			session.setAttribute("userName", loginUser.getName()); // �꽭�뀡�뿉 蹂��닔�벑濡�

			object.addProperty("id", loginUser.getUserId());
			object.addProperty("msg", "Success");

			String json = gson.toJson(object);

			return json.toString();
		}
	}
		
	
	// 회원 가입 코드
		@RequestMapping(value = "/join.do", method = RequestMethod.GET) // 회원가입 클릭시(join.do를 누르면 /입력이되면) 회원가입 Form을 띄어주는 것
		public String member(Model model) { // 메소드 구성에 필요한 3가지 중요
			model.addAttribute("user", new UserVO());
			return "/member/joinForm";

		}

		// 아이디 중복체크하는 코드
		@RequestMapping(value = "/checkid.do", method = RequestMethod.GET)
		// 아이디 중복검사를 입력하면 실행
		public String dupCheckId(@RequestParam("userId") String userId, Model model) {
			// 이는 요청에서 userId 값하나씩 읽을 때 쓰는 것 으로 우리가 아는
			// String userId = request.getParameter("userId")와 같은 의미를 지닌다.
	//  접근지정자  출력         이름                           입   (여러개(modelAttribute)가 아닌 userId값을 하나 읽어서 그안에 값을 userId에 넣어라 어떤형태?? String형태 ,model은 빈가방을 만들어 보낸다		
			// modelAttribute은 여러개의 값을 읽는 어노테이션이다.
			String message = "";
			int reDiv = 0;
			try {
				UserVO loginUser = this.loginService.getUser2(userId);
				// 내가 입력한 userId값과 User에서 가져온 값 loginUser값을 비교한다
				message = "이미 사용중인 아이디";
				reDiv = 0; // 0이면 쓸수 없다 즉 에러가 없다면 쓸수 없다는 얘기
				userId = ""; // id값을 비운다
			} catch (EmptyResultDataAccessException e) {
				message = "사용 가능한 아이디";
				reDiv = 1; // 1이면 사용할수 있다
			}
			model.addAttribute("user", new UserVO());
			model.addAttribute("message", message);
			model.addAttribute("reDiv", reDiv);
			model.addAttribute("userId", userId); // 즉 위에서 본 코드처럼 reDiv일 경우 userId값이 null값 비워진다.
			return "/member/joinForm";

		}

		@RequestMapping(value = "/join.do", method = RequestMethod.POST) // 이부분은 회원가입이 완료되게 하는 코드이다.
		public String onSubmit(@Valid UserVO user, BindingResult result, Model model) throws Exception {
			// DTO객체에 비어있는 지 유효성검사를 실행 그 결과 값을 result에 넣기
			if (result.hasErrors()) {
				model.addAttribute(result.getModel()); // 값이 비어 있다면 joinForm으로 전달
				return "/member/joinForm";
			}
			
			// passwd 암호화
			String hashPass = BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt(12));
			user.setPasswd(hashPass); //

			// 주민번호 암호화
			String jumin1 = user.getJumin().substring(0, 6);
			String jumin2 = user.getJumin().substring(6);

			String key = "jangan-1182-Key!!";
			AES256Util aes256 = new AES256Util(key);

			String hashjumin = aes256.aesEncode(jumin2);
			String encjumin = jumin1 + "-" + hashjumin;

			user.setJumin(encjumin);

			try { // 데이터가 정상적으로 들어왔다면 실행
				this.loginService.insertUser(user);
				model.addAttribute("message", "다음과 같이 회원가입이 완료");
				model.addAttribute("user", user);
				return "/member/login"; // 회원가입이 성공하였다면 login 이동
			} catch (DataIntegrityViolationException e) {
				model.addAttribute("esgMsg", "회원 가입이 안되었습니다 다시 시도해 주세요^^");
				return "/member/joinForm"; // 회원가입이 실패되었다면 joinForm으로 이동
			}
		}

	@RequestMapping(value = "/detail.do", method = RequestMethod.GET) //
	public String detail(HttpSession session, Model model) {
		String userId = session.getAttribute("userId").toString();
		UserVO getUser = this.loginService.getUser(userId);
		getUser.setJumin(getUser.getJumin().substring(0, 13) );
		model.addAttribute("min", "mod");//@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		model.addAttribute("user", getUser);

		return "/member/joinForm";
	}

	@RequestMapping(value = "/detail.do", method = RequestMethod.POST) // �엯�젰�뤌 �궡�슜 ���옣
	public String detailProc(@Valid UserVO user, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "/member/joinForm";
		}

		// passwd 암호화
		String hashPass = BCrypt.hashpw(user.getPasswd(), BCrypt.gensalt(12));
		user.setPasswd(hashPass); 

		if (this.loginService.updateUser(user) != 0) { 
			return "redirect:/board/list.do";
		} else { //
			return "/member/joinForm";
		}
	}

	@RequestMapping(value = "/findId.do", method = RequestMethod.GET)
	public String findId() {
		return "/member/findId";
	}

	@RequestMapping(value = "/findId.do", method = RequestMethod.POST)
	public String findIdProc(@Valid UserVO userVO, BindingResult result, Model model, HttpSession session) throws Exception {
		if (result.hasFieldErrors("name") || result.hasFieldErrors("email")) {
			model.addAllAttributes(result.getModel());
			return "/member/findId";
		}

		UserVO findIduser = this.loginService.findId(userVO);
		if (findIduser == null) {
			model.addAttribute("errCode", 1);
			model.addAttribute("userId", "");
			
			return "/member/findId";

		} else {
			model.addAttribute("userId", findIduser.getUserId());
			return "/member/find_id";
		}
	}

	@RequestMapping(value = "/findPass.do", method = RequestMethod.GET)
	public String findPass() {
		return "/member/findPass";
	}

	@RequestMapping(value = "/findPass.do", method = RequestMethod.POST)
	public String findPassProc(@Valid UserVO userVO, BindingResult result, Model model, HttpSession session){

		if (result.hasFieldErrors("userId") || result.hasFieldErrors("name") 
				|| result.hasFieldErrors("email")) {
			model.addAllAttributes(result.getModel());
			return "/member/findPass";
		}


		UserVO findPwd = this.loginService.findPass(userVO);

		if (findPwd == null) {
			
			return "/member/joinForm";
		} else {
			String passwd = "";
			// 문자열 로 pass를 초기화
			for (int i = 0; i < 10; i++) { // 10자리의 숫자를 통해서
				passwd += (char) ((Math.random() * 26) + 97);
				// 문자이기 때문에 char형태 문자형으로
			}
			// for문을 이용하여 10자리의 임의값을 랜덤으로 생성한다.
			// 영소문자 a-z 출력하기로 (아스키코드 97~122)로
			// 26을 곱하고 97을 더한 값을 char형으로 형변환을 한다면 문로 변경
			// 10번 반복하여 pass에 값을 하나씩 붙인다.

			userVO.setPasswd(passwd); // 임의로 변경된 비밀번호를 DTO user의 pass를 전달하여 변경한다.
			
			// passwd 암호화
			String hashPass = BCrypt.hashpw(userVO.getPasswd(), BCrypt.gensalt(12));
			userVO.setPasswd(hashPass); //
			
			String id = findPwd.getUserId();
			model.addAttribute("user", passwd);
			model.addAttribute("users",id);
			
			this.loginService.updatePwd(userVO);		
			return "/member/find_pw";
		}
	}
/*
	@RequestMapping(value = "/edituser", method = RequestMethod.GET)
	public String toUserEditView(HttpSession session, Model model)
			throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String userId = session.getAttribute("userId").toString();
		UserVO loginUser = this.loginService.getUser(userId);

		if (loginUser == null) {
			model.addAttribute("userId", "");
			model.addAttribute("errCode", 1); // �벑濡앸릺吏��븡�� �븘�씠�뵒

			return "/member/login";
		} else { // 二쇰�쇰쾲�샇 蹂듯샇�솕
			String jumin1 = loginUser.getJumin().substring(0, 6);
			String jumin2 = loginUser.getJumin().substring(7);

			String key = "jangan-1182-Key!!";
			AES256Util aes256 = new AES256Util(key);

			String decjumin = aes256.aesDecode(jumin2);
			decjumin = jumin1 + "-" + decjumin;

			loginUser.setJumin(decjumin);
			model.addAttribute("userVO", loginUser);
			return "/member/editForm";
		}
	}

	@RequestMapping(value = "/edituser", method = RequestMethod.POST)
	public String onEditSave(@Valid UserVO userVO, BindingResult result, Model model) throws Exception {

		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "/member/editForm";
		}

		userVO.setPasswd(BCrypt.hashpw(userVO.getPasswd(), BCrypt.gensalt(12)));

		try {
			this.loginService.updateUser(userVO);
			model.addAttribute("userVO", userVO);
			return "/board/list.do";
		} catch (DataAccessException e) {
			result.reject("error.duplicate.user");
			model.addAllAttributes(result.getModel());
			return "/member/editForm";
		}
	}*/
	
	
	@RequestMapping(value = "/delete.do")
	public String toUserDelete(Model model) {
		model.addAttribute("userVO", new UserVO());
		return "/member/deleteform";
	}

	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String toUserDeletePro(@ModelAttribute @Valid UserVO userVO, BindingResult result, Model model,
			HttpSession session) throws Exception {

		if (result.hasFieldErrors("userId")) {
			model.addAllAttributes(result.getModel());
			return "/member/deleteform";

		}

		try {
			this.loginService.deleteUser(userVO.getUserId());

			session.invalidate();

			return "redirect:/member/login.do";

		} catch (EmptyResultDataAccessException e) {
			model.addAttribute("errMsg", "탈퇴 실패");
			return "/member/deleteform";
		}

	}
	
	
	
	////// Test ing
	
	@RequestMapping(value = "/mylist.do", method = RequestMethod.POST) //
	public String myList(HttpSession session, Model model) {
		String userId = session.getAttribute("userId").toString();
		UserVO getUser = this.loginService.getUser(userId);
		model.addAttribute("min", "my");//@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		model.addAttribute("userId", getUser);

		return "/board/list.do";
	}
}
