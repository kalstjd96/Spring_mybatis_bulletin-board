package com.jang.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jang.bbs.model.UserVO;

@Mapper
public interface LoginMapper {
	UserVO findUser(UserVO userVO);
	UserVO getUser(String userId);
	
	int updateUser(UserVO userVO);
	int insertUser(UserVO userVO);
	
	UserVO findId(UserVO userVO);
	UserVO findPass(UserVO userVO);
	
	UserVO getUser2(String userId);
	UserVO deleteUser(String userId);
	
	UserVO updatePwd(UserVO userVO);
	
}
