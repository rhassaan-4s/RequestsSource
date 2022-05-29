package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="annual_vac_limit")
public class AnnualVacLimit implements Auditable,Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="annual_vac_limit_seq")
	@SequenceGenerator(name="annual_vac_limit_seq",sequenceName="annual_vac_limit_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	
	private String vac_id;
	
	private String vac_period;
	
	private String vac_limit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVac_id() {
		return vac_id;
	}

	public void setVac_id(String vac_id) {
		this.vac_id = vac_id;
	}

	public String getVac_period() {
		return vac_period;
	}

	public void setVac_period(String vac_period) {
		this.vac_period = vac_period;
	}

	public String getVac_limit() {
		return vac_limit;
	}

	public void setVac_limit(String vac_limit) {
		this.vac_limit = vac_limit;
	}
	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "AnnualVacLimit Addition";
	}

}
