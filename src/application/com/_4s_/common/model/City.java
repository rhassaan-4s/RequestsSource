package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com._4s_.auditing.model.Auditable;

/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "common_city"
 */
@Entity
@Table (name = "common_city")
public class City implements Serializable,Auditable {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String englishDescription;
	
	private String code;
	@ManyToOne
	@JoinColumn (name="country")
	private Country country;
	@Transient
	private final Log log = LogFactory.getLog(getClass());
	private Boolean isDefault = new Boolean (false);
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
	
	public String getEnglishDescription() {
		return englishDescription;
	}
	public void setEnglishDescription(String englishDescription) {
		this.englishDescription = englishDescription;
	}
	/**
	 * @return Returns the position.
	 * 
	 * @hibernate.property name="position" type="int" update="true" insert="true" column="position"
	 */
	public int getPosition() {
		int position;
		if(this.getCountry()==null){
			log.debug("No Country attached");
			position = -1 ;
		} else {
			if (log.isDebugEnabled()) {
				if (this.getCountry().getCities().contains(this)) {
					log.debug("message EXISTS in thread");
				} else {
					log.debug("message DOES NOT EXIST in thread");
				}
				log.debug("Position: "+this.getCountry().getCities().indexOf(this));
			}
			position = this.getCountry().getCities().indexOf(this);
		}
		return position;
	}
	public void setPosition(int position) {
		
	}
	
	
	/**
	 * @return Returns the country.
	 * @hibernate.many-to-one column = "country" class = "com._4s_.common.model.Country"   
	 */
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		if ((country!=null)&&(country.getCities()!=null)) {
			// this could only happen if a city changes its country :-)
			//country.getCities().remove(this);
		}
		this.country = country;
	}

	/**
	 * @return Returns the description.
	 * @hibernate.property column = "description" not-null = "true"
	 */
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return new ToStringBuilder(this)
		.append("description", this.description).toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof City)) {
			return false;
		}
		City rhs = (City) o;
		return new EqualsBuilder().append(this.description, rhs.getDescription()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.description).toHashCode();
	}
	public String getEntityDisplayName() {
		
		return this.description;
	}
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
