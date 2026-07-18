package com._4s_.dbUpdate.service;


import com._4s_.common.service.BaseManager;
public interface DbManager extends BaseManager {
	
	public abstract void executeQuery(String query);
}
