package kr.ac.hansung.cse.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.Members;

@Repository
public class UserDAO {

	@Autowired
	public SqlSession sqlSession;

	public Members getUserOne(String common, String col) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(col.equals("userId")) {
			map.put("userId",common);
		}
		return sqlSession.selectOne("getUserOne",map);
	}

	public int userJoin(Members members) {
		return sqlSession.insert("userJoin",members);
	}

}
