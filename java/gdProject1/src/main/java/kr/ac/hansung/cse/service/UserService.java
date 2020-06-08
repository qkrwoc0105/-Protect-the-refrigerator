package kr.ac.hansung.cse.service;

import kr.ac.hansung.cse.model.Members;

public interface UserService {
	Members getUserOne(String common, String col);

	int userJoin(Members members);
}
