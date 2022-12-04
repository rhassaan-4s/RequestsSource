package com._4s_.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "common_country"
 */
@Entity
@Table (name = "common_country")
public class Country implements Serializable,Auditable {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	private String description;
	private String maleNationality;
	private String femaleNationality;
	
	@OneToMany (mappedBy ="country",cascade=CascadeType.ALL)
	private List<City>cities = new ArrayList<City>();
	private Boolean isDefault = new Boolean(false);
	/**
	 * @return Returns the id.
	 * @hibernate.id generator-class = "native"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return Returns the description.
	 * @hibernate.property column = "description"
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	/**
	 * @hibernate.list table = "common_city" cascade = "save-update" inverse = "true"
	 * @hibernate.collection-key column = "country"
	 * @hibernate.collection-index column = "position"
	 * @hibernate.collection-one-to-many class = "com._4s_.common.model.City" 
	 */
	public List getCities() {
		return cities;
	}
	
	
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("description", this.description).toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Country)) {
			return false;
		}
		Country rhs = (Country) o;
		return new EqualsBuilder().append(this.description, rhs.getDescription()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.description).toHashCode();
	}
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.description;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public String getFemaleNationality() {
		return femaleNationality;
	}
	public void setFemaleNationality(String femaleNationality) {
		this.femaleNationality = femaleNationality;
	}
	public String getMaleNationality() {
		return maleNationality;
	}
	public void setMaleNationality(String maleNationality) {
		this.maleNationality = maleNationality;
	}


}
