package com._4s_.timesheet.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.restServices.json.TimesheetActivityWrapper;

@Entity
@Table(name="activity")
public class TimesheetActivity {

	@Id
	@GenericGenerator(name="activity_seq",strategy="com._4s_.requestsApproval.dao.ActivityStringKeyGenerator")//sequenceName="time_sheet_activity_seq"
	@GeneratedValue(generator="activity_seq")
	private String activity;
	private String name;
	private String ename;
	
	
	public String getActivity() {
		return activity;
	}


	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public boolean equals(TimesheetActivity obj) {
		// TODO Auto-generated method stub
		return obj.getActivity().equals(this.getActivity());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getActivity()+"-"+this.getName();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.activity)

		.toHashCode();
	}
	
	public TimesheetActivityWrapper getWrapper() {
		TimesheetActivityWrapper wrapper = new TimesheetActivityWrapper();
		wrapper.setCode(this.activity);
		wrapper.setArName(this.name);
		wrapper.setEnName(this.ename);
		return wrapper;
	}
	
	public Long getId() {
		return Long.parseLong(activity);
	}
}
