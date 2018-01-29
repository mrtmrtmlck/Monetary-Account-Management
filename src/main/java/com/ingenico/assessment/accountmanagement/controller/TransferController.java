package com.ingenico.assessment.accountmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingenico.assessment.accountmanagement.model.Account;
import com.ingenico.assessment.accountmanagement.model.Transfer;
import com.ingenico.assessment.accountmanagement.service.TransferService;

@RestController
@RequestMapping("/api")
public class TransferController {

	@Autowired
	private TransferService transferService;

	/**
	 * Simply creates a new account with name and balance.
	 */
	@PostMapping("/newAccount")
	public Account createAccount(@Valid @RequestBody Account account) {
		return transferService.saveAccount(account);
	}

	/**
	 * Fully isolated to avoid dirty reads. Because this method does more than one commit
	 *  -update two accounts- it is designed as transaction. In case any error occurrence, it rolls back.
	 */
	@PostMapping("/transfer")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ResponseEntity<Account> transferMoney(@RequestBody Transfer transfer) {
		Account senderAccount = transferService.getAccountById(transfer.getSenderId());
		if (senderAccount == null) {
			// if there is no account with given id
			// response with not found status
			return ResponseEntity.notFound().build();
		}

		if (senderAccount.getBalance() < transfer.getAmount()) {
			// if given transfer amount is more than sender account's balance
			// response with bad request status
			return ResponseEntity.badRequest().build();
		}

		if (transfer.getAmount() < 0) {
			// if given transfer amount is less than 0
			// response with bad request
			return ResponseEntity.badRequest().build();
		}

		Account receiverAccount = transferService.getAccountById(transfer.getReceiverId());
		if (receiverAccount == null) {
			return ResponseEntity.notFound().build();
		}

		senderAccount.setBalance(senderAccount.getBalance() - transfer.getAmount());
		receiverAccount.setBalance(receiverAccount.getBalance() + transfer.getAmount());

		transferService.saveAccount(senderAccount);
		transferService.saveAccount(receiverAccount);

		return ResponseEntity.ok(senderAccount);
	}
}