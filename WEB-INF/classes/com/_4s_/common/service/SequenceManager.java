package com._4s_.common.service;

import com._4s_.common.model.LastSequence;

public interface SequenceManager {
	public abstract Long getSequenceByClassName (String tableName);
	public abstract void saveSequence(LastSequence lastSequence);
	
}
