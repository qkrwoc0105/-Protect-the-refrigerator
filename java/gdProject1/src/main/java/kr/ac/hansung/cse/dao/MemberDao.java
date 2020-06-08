package kr.ac.hansung.cse.dao;

import kr.ac.hansung.cse.vo.MemberVO;

public interface MemberDao {

	//회원가입
	public void signup(MemberVO vo) throws Exception;
	
	//로그인
	//public MemberVO login(MemberVO vo) throws Exception;

}
