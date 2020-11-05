package com.ldx.mapper;

import com.ldx.pojo.Account;

import java.util.List;

public interface AccountMapper {

    List<Account> queryAccountList() throws Exception;

}
