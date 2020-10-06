package com.project.faurExchange.tables;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.project.faurExchange.exception.ExceptionUser;
import com.project.faurExchange.model.User;

@Component
public class UserDB {

	private List<User> users;

	public UserDB() {
		this.users = new ArrayList<>();
	}

	public boolean checkIfExists(User user) {
		return users.contains(user);
	}

	public User findUser(String username) throws ExceptionUser {
		for (User user : users) {
			if (username.equals(user.getUsername())) {
				return user;
			}
		}
		throw new ExceptionUser("This user is not exist");
	}

	public void addUser(User user) {
		users.add(user);
	}

	@PostConstruct
	public void init() {
		if (users == null) {
			users = new ArrayList<>();
		}
		for (int i = 1; i <= 10; ++i) {
			User user = new User();
			user.setCardNo("4000-1192-4325-100" + i);
			user.setUsername("user" + i);
			user.setCreditLimit(10 + i);
			users.add(user);
		}
	}

	public void takeMoneyFromUserAccount(int money, String userID) {
		for (User user : users) {
			if ((user.getUsername()).equals(userID))
				user.removeBalance(money);
		}
	}

}