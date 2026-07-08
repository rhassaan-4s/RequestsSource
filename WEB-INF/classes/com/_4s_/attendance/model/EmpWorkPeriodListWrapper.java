package com._4s_.attendance.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com._4s_.common.model.EmpBasic;

public class EmpWorkPeriodListWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4450196460062228004L;

	private EmpBasic empCode;
	private List<EmpWorkPeriod> empPeriods = new ArrayList<EmpWorkPeriod>();
	public EmpBasic getEmpCode() {
		return empCode;
	}
	public void setEmpCode(EmpBasic empCode) {
		this.empCode = empCode;
	}
	public List<EmpWorkPeriod> getEmpPeriods() {
//		System.out.println("********************####"+empPeriods.size());
		return empPeriods;
	}
	public void setEmpPeriods(List<EmpWorkPeriod> empPeriods) {
		System.out.println("********************$$$$"+empPeriods.size());
		this.empPeriods = empPeriods;
	}
	

	
}
