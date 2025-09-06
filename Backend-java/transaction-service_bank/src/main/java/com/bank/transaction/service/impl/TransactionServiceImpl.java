package com.bank.transaction.service.impl;

import com.bank.transaction.dto.AccountUpdateRequest;
import com.bank.transaction.dto.TransferRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.exception.TransactionException;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);


private final TransactionRepository transactionRepository;
private final RestTemplate restTemplate; // load-balanced: can call by service name


// Endpoints used on account-service. Replace with actual endpoints or service name+path as required.
// If using Eureka with service registration, use http://account-service/api/accounts/{id}/debit etc.
private static final String ACCOUNT_DEBIT_URL = "http://account-service/api/accounts/{id}/debit";
private static final String ACCOUNT_CREDIT_URL = "http://account-service/api/accounts/{id}/credit";


@Override
@Transactional
public TransactionResponse transfer(TransferRequest request) {
String txId = UUID.randomUUID().toString();


Transaction tx = Transaction.builder()
.txId(txId)
.fromAccountId(request.getFromAccountId())
.toAccountId(request.getToAccountId())
.amount(request.getAmount())
.type("TRANSFER")
.timestamp(new Date())
.status("PENDING")
.build();


transactionRepository.save(tx);
log.info("Created transaction record with txId={}", txId);


//Prepare debit request for fromAccount
AccountUpdateRequest debitReq = new AccountUpdateRequest(request.getFromAccountId(), request.getAmount(), txId);


try {
//1) Debit fromAccount
@SuppressWarnings("rawtypes")
ResponseEntity<Map> debitResp = restTemplate.postForEntity(ACCOUNT_DEBIT_URL, debitReq, Map.class, request.getFromAccountId());
log.info("Debit response: status={} body={}", debitResp.getStatusCode(), debitResp.getBody());


if (!debitResp.getStatusCode().is2xxSuccessful()) {
tx.setStatus("FAILED");
transactionRepository.save(tx);
throw new TransactionException("Debit failed for account: " + request.getFromAccountId());
}


//2) Credit toAccount
AccountUpdateRequest creditReq = new AccountUpdateRequest(request.getToAccountId(), request.getAmount(), txId);
@SuppressWarnings("rawtypes")
ResponseEntity<Map> creditResp = restTemplate.postForEntity(ACCOUNT_CREDIT_URL, creditReq, Map.class, request.getToAccountId());
log.info("Credit response: status={} body={}", creditResp.getStatusCode(), creditResp.getBody());



if (!creditResp.getStatusCode().is2xxSuccessful()) {
	// Compensation: try to refund the debited amount back to fromAccount
	log.warn("Credit failed, attempting compensation (refund) to fromAccount {}", request.getFromAccountId());
	try {
	AccountUpdateRequest refundReq = new AccountUpdateRequest(request.getFromAccountId(), request.getAmount(), txId);
	restTemplate.postForEntity("http://account-service/api/accounts/{id}/credit", refundReq, Map.class, request.getFromAccountId());
	} catch (RestClientException compEx) {
	log.error("Compensation (refund) failed, manual intervention required for txId={}", txId, compEx);
	}


	tx.setStatus("FAILED");
	transactionRepository.save(tx);
	throw new TransactionException("Credit failed for account: " + request.getToAccountId());
	}



//Both debit and credit successful
tx.setStatus("SUCCESS");
transactionRepository.save(tx);
return new TransactionResponse(txId, "SUCCESS", "Transfer completed successfully");


} catch (RestClientException ex) {
log.error("Error while calling account-service: {}", ex.getMessage(), ex);
tx.setStatus("FAILED");
transactionRepository.save(tx);
throw new TransactionException("Error while calling account-service: " + ex.getMessage(), ex);
} catch (TransactionException tex) {
log.error("Transaction failed: {}", tex.getMessage());
throw tex;
} catch (Exception e) {
log.error("Unexpected error: {}", e.getMessage(), e);
tx.setStatus("FAILED");
transactionRepository.save(tx);
throw new TransactionException("Unexpected error during transfer", e);
}
}
}