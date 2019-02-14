package com._4s_.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "common_region")
public class Region implements Serializable,Auditable
{
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String description;
	
	private String code;
	
	@ManyToOne
	@JoinColumn (name="country")
	private Country country;

	@OneToMany
	@JoinColumn (name="region")
	private List<City>cities = new ArrayList<City>();

	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("description", this.description).toString();
	}

	public boolean equals(Object o) 
	{
		if (o == this) 
		{
			return true;
		}
		if (!(o instanceof Region)) 
		{
			return false;
		}
		Region r = (Region) o;
		return new EqualsBuilder().append(this.description, r.getDescription()).isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder(2090939697, 874530185).append(this.description).toHashCode();
	}
	public String getEntityDisplayName() 
	{
		return this.description;
	}
	
	
	
	
	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
