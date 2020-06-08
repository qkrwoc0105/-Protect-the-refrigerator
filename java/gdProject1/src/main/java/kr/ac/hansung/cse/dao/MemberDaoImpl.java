package kr.ac.hansung.cse.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.vo.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao {

	//회원가입
	//@javax.inject.Inject()
	@Inject
	SqlSession sql;

	@Override
	public void signup(MemberVO vo) throws Exception {

		sql.insert("memberMapper.register", vo);
	}

	/*
	 * @Override public MemberVO login(MemberVO vo) throws Exception {
	 * 
	 * return null; }
	 */
	

}
