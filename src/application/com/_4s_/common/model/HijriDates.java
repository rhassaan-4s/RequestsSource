package com._4s_.common.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table (name = "common_dates")
public class HijriDates {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	private Date miladi;
	private String hijri;
	public String getHijri() {
		return hijri;
	}
	public void setHijri(String hijri) {
		this.hijri = hijri;
	}
	public Date getMiladi() {
		return miladi;
	}
	public void setMiladi(Date miladi) {
		this.miladi = miladi;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
