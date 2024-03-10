package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

import com.getjavajob.training.timashovy.socialnetwork.common.account.Password;

public interface LoginDao {

    Password findPasswordByEmail(String email);

}
