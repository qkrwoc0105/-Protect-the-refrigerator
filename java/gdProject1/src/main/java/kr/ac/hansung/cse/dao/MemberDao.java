package kr.ac.hansung.cse.dao;

import kr.ac.hansung.cse.vo.MemberVO;

public interface MemberDao {

	//ȸ������
	public void signup(MemberVO vo) throws Exception;
	
	//�α���
	//public MemberVO login(MemberVO vo) throws Exception;

}
