package com._4s_.security.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("users")
public class UsersMap {
	private Map users = new HashMap();

	public Map getUsers() {
		return users;
	}

	public void setUsers(Map users) {
		this.users = users;
	}
}
