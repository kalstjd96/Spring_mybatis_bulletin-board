package com.jang.bbs.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jang.bbs.mapper.LoginMapper;
import com.jang.bbs.model.UserVO;

@Service(value = "loginService")
public class LoginService {

	@Resource(name = "loginMapper") // autowired
	private LoginMapper loginMapper;
	
	public UserVO findUser(UserVO userVO) {
		return loginMapper.findUser(userVO);
	}
	
	public UserVO getUser(String userId) {
		return loginMapper.getUser(userId);
	}
	
	public int updateUser(UserVO userVO) {
		return loginMapper.updateUser(userVO);
	}
	
	public int insertUser(UserVO userVO) {
		return loginMapper.insertUser(userVO);
	}
	
	public UserVO findId(UserVO userVO) {
		return loginMapper.findId(userVO);
	}
	
	public UserVO getUser2(String userId) {
		return loginMapper.getUser2(userId);
	}
	
	public UserVO deleteUser(String userId) {
		return loginMapper.deleteUser(userId);
	}
	public UserVO updatePwd(UserVO userVO) {
		return loginMapper.updatePwd(userVO);
	}
	
	
	
	public UserVO findPass(UserVO userVO) {
		return loginMapper.findPass(userVO);
	}
}