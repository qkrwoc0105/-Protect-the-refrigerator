package kr.ac.hansung.cse.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.MemberDao;
import kr.ac.hansung.cse.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	MemberDao dao;

	@Override
	public void signup(MemberVO vo) throws Exception {

		dao.signup(vo);

	}

}
