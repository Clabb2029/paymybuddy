package com.pay_my_buddy.paymybuddy;

import com.pay_my_buddy.paymybuddy.service.BankTransferService;
import com.pay_my_buddy.paymybuddy.service.RelationService;
import com.pay_my_buddy.paymybuddy.service.TransferService;
import com.pay_my_buddy.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymybuddyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Application ready!");
	}

}
