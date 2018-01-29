package com.ingenico.assessment.accountmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingenico.assessment.accountmanagement.model.Account;
import com.ingenico.assessment.accountmanagement.repository.AccountRepository;

@Service("transferService")
public class TransferServiceImpl implements TransferService {

	@Autowired
	private AccountRepository accountRepositroy;
	
	@Override
	public Account getAccountById(long id) {
		return accountRepositroy.findOne(id);
	}

	@Override
	public Account saveAccount(Account account) {
		return accountRepositroy.save(account);
	}
}
