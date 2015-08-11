package com.sample.domain.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.domain.model.Account;
import com.sample.domain.model.Todo;

public interface AccountRepository  extends JpaRepository<Account, String>{
}
