package com.sample.domain.service.account;

import com.sample.domain.model.Account;

public interface AccountSharedService {
    Account findOne(String username);
}