package com._4s_.restServices.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com._4s_.common.service.BaseManager;
import com._4s_.requestsApproval.model.LoginUsersRequests;
import com._4s_.restServices.json.AttendanceRequest;
import com._4s_.security.model.User;


@Transactional
public interface RequestsService {// extends BaseManager {

	public User login(String username, String password);

	public LoginUsersRequests signInOut(AttendanceRequest userRequest);

	public LoginUsersRequests userRequest(AttendanceRequest userRequest);
}
