package com.pay_my_buddy.paymybuddy;

import com.pay_my_buddy.paymybuddy.model.BankTransfer;
import com.pay_my_buddy.paymybuddy.model.Transfer;
import com.pay_my_buddy.paymybuddy.model.User;
import com.pay_my_buddy.paymybuddy.service.BankTransferService;
import com.pay_my_buddy.paymybuddy.service.TransferService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymybuddyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private BankTransferService bankTransferService;

	@Autowired
	private TransferService transferService;

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<BankTransfer> bankTransfers = bankTransferService.getBankTransfers();
		Iterable<Transfer> transfers = transferService.getTransfers();
		System.out.println(bankTransfers);
		System.out.println(transfers);
/*		bankTransfers.forEach(bt -> System.out.println(bt.getAmount()));
		transfers.forEach(t -> System.out.println(t.getAmount()));*/
	}

}
