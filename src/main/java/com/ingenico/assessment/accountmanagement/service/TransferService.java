package com.ingenico.assessment.accountmanagement.service;

import com.ingenico.assessment.accountmanagement.model.Account;

public interface TransferService {
	public Account getAccountById(long id);
	public Account saveAccount(Account account);
}
