package com._4s_.attendance.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;
import com.ibm.icu.util.Calendar;

@Entity
@Table (name = "workperiods_o")

public class WorkPeriod implements Serializable,Auditable {
	@Id
	@ManyToOne
	@JoinColumn(name = "workperiods")
	private WorkPeriodMaster workperiods;
	@Id
	private Timestamp start_date=new Timestamp(Calendar.getInstance().getTimeInMillis());
	private Timestamp end_date=Timestamp.valueOf("2050-12-31 23:59:59");
	private String is_default = "0";//0 or 1
	private Timestamp time_in;
	private Timestamp time_out;
	private Integer required_hours=0;
	private Integer consider_delay=1;//0 or 1
	private Integer allowance_minute=0;//late sign in minuted allowed
	private Integer consider_early = 1;//0 or 1
	private Integer allowance_minute2 = 0; //early dismissal minutes allowed
	private Integer consider_overtime=1;//0 or 1
	private Integer min_overtime_minutes=0;
	private Integer consider_absence=1;//0 or 1
	private Integer ratio_of_day=1;
	private Integer min_attend_hours;
	private Integer ignore_attend_before=0;
	private Integer max_attend_before=0;
	private Integer ignore_attend_after=0;
	private Integer max_attend_after=0;
	private Integer calc_attend_rule=1;//1 before+during+after work period
										//2 during+after work period
										//3 before + during work period
										//4 during work period
	private Integer calc_over_rule=1;//1 before+during+after work period
									//2 during+after work period
									//3 before + during work period
									//4 after work period
	private Integer affect_sal_less_time=1;//1 without deduction
											//2 target-achieved
											//4 target-achieved-late sign in
	
	
	
	public Long getId() {
		return null;
	}

	
	

	public WorkPeriodMaster getWorkperiods() {
		return workperiods;
	}




	public void setWorkperiods(WorkPeriodMaster workperiods) {
		this.workperiods = workperiods;
	}




	public Timestamp getStart_date() {
		return start_date;
	}




	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}




	public Timestamp getEnd_date() {
		return end_date;
	}




	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}




	public String getIs_default() {
		return is_default;
	}




	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}




	public Timestamp getTime_in() {
		return time_in;
	}




	public void setTime_in(Timestamp time_in) {
		this.time_in = time_in;
	}




	public Timestamp getTime_out() {
		return time_out;
	}




	public void setTime_out(Timestamp time_out) {
		this.time_out = time_out;
	}




	public Integer getRequired_hours() {
		return required_hours;
	}




	public void setRequired_hours(Integer required_hours) {
		this.required_hours = required_hours;
	}




	public Integer getConsider_delay() {
		return consider_delay;
	}




	public void setConsider_delay(Integer consider_delay) {
		this.consider_delay = consider_delay;
	}




	public Integer getAllowance_minute() {
		return allowance_minute;
	}




	public void setAllowance_minute(Integer allowance_minute) {
		this.allowance_minute = allowance_minute;
	}




	public Integer getConsider_early() {
		return consider_early;
	}




	public void setConsider_early(Integer consider_early) {
		this.consider_early = consider_early;
	}




	public Integer getAllowance_minute2() {
		return allowance_minute2;
	}




	public void setAllowance_minute2(Integer allowance_minute2) {
		this.allowance_minute2 = allowance_minute2;
	}




	public Integer getConsider_overtime() {
		return consider_overtime;
	}




	public void setConsider_overtime(Integer consider_overtime) {
		this.consider_overtime = consider_overtime;
	}




	public Integer getMin_overtime_minutes() {
		return min_overtime_minutes;
	}




	public void setMin_overtime_minutes(Integer min_overtime_minutes) {
		this.min_overtime_minutes = min_overtime_minutes;
	}




	public Integer getConsider_absence() {
		return consider_absence;
	}




	public void setConsider_absence(Integer consider_absence) {
		this.consider_absence = consider_absence;
	}




	public Integer getRatio_of_day() {
		return ratio_of_day;
	}




	public void setRatio_of_day(Integer ratio_of_day) {
		this.ratio_of_day = ratio_of_day;
	}




	public Integer getMin_attend_hours() {
		return min_attend_hours;
	}




	public void setMin_attend_hours(Integer min_attend_hours) {
		this.min_attend_hours = min_attend_hours;
	}




	public Integer getIgnore_attend_before() {
		return ignore_attend_before;
	}




	public void setIgnore_attend_before(Integer ignore_attend_before) {
		this.ignore_attend_before = ignore_attend_before;
	}




	public Integer getMax_attend_before() {
		return max_attend_before;
	}




	public void setMax_attend_before(Integer max_attend_before) {
		this.max_attend_before = max_attend_before;
	}




	public Integer getIgnore_attend_after() {
		return ignore_attend_after;
	}




	public void setIgnore_attend_after(Integer ignore_attend_after) {
		this.ignore_attend_after = ignore_attend_after;
	}




	public Integer getMax_attend_after() {
		return max_attend_after;
	}




	public void setMax_attend_after(Integer max_attend_after) {
		this.max_attend_after = max_attend_after;
	}




	public Integer getCalc_attend_rule() {
		return calc_attend_rule;
	}




	public void setCalc_attend_rule(Integer calc_attend_rule) {
		this.calc_attend_rule = calc_attend_rule;
	}




	public Integer getCalc_over_rule() {
		return calc_over_rule;
	}




	public void setCalc_over_rule(Integer calc_over_rule) {
		this.calc_over_rule = calc_over_rule;
	}




	public Integer getAffect_sal_less_time() {
		return affect_sal_less_time;
	}




	public void setAffect_sal_less_time(Integer affect_sal_less_time) {
		this.affect_sal_less_time = affect_sal_less_time;
	}


	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "work periods";
	}

	public String toString() {
		System.out.println("tostring");
		return new ToStringBuilder(this)
		.append("code", this.workperiods)
//		.append("startdate", this.start_date)
		.toString();
	}

	public boolean equals(Object o) {
		System.out.println("equals");
		if (o == this) {
			return true;
		}
		if (!(o instanceof WorkPeriod)) {
			return false;
		}
		WorkPeriod rhs = (WorkPeriod) o;
		return new EqualsBuilder().append(this.workperiods, rhs.getWorkperiods()).append(this.start_date, rhs.getStart_date()).isEquals();
	}

	public int hashCode() {
		System.out.println("hashCode");
		return new HashCodeBuilder(2090939697, 874530185).append(this.workperiods).append(this.start_date).toHashCode();
	}

}
