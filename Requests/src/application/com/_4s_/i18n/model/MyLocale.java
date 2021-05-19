package com._4s_.i18n.model;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @author Heba Fouad
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "i18n_locale"
 */
@Entity
@Table (name = "i18n_locale")
public class MyLocale implements Serializable,Auditable {
	@Id
	private Long id;

	private String language;

	private String country ;

	private String variant ;
	
	private String code ;
	
	private Boolean isDefault = new Boolean(false);

	public MyLocale() {
		
	}
	
	public MyLocale(String code, String country, String variant) {
		this.code = code;
		this.country = country;
		this.variant = variant;
	}
	/**
	 * @return
	 * 
	 * @hibernate.id column = "id" generator-class = "native" unsaved-value =
	 *               "null"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locale getLocale() {
		return new Locale(code, country, variant);
	}

	/**
	 * @return
	 * 
	 * @hibernate.property column = "country"
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return
	 * 
	 * @hibernate.property column = "language" not-null="true"
	 */
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return
	 * 
	 * @hibernate.property column = "variant"
	 */
	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}
	/**
	 * @return
	 * 
	 * @hibernate.property column = "code"
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return
	 * 
	 * @hibernate.property column = "isDefault"
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof MyLocale)) {
			return false;
		}
		MyLocale rhs = (MyLocale) object;
		return new EqualsBuilder()
					.append(this.language, rhs.language)
					.append(this.country, rhs.country)
					.append(this.code, rhs.code)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
					.append(this.language)
					.append(this.country)
					.append(this.code)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
/*		return new ToStringBuilder(this)
					.append("language",this.language)
					.append("country", this.country)
					.append("code", this.code)
					
				.toString();
				*/
		return language;
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.language;
	}
}
