package com._4s_.HR.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.TreeDivisions;
import com._4s_.common.util.LocaleUtil;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_qualification_division")
public class HRQualificationDivision  extends TreeDivisions {
			
			@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_qual_division_seq")
			@SequenceGenerator(name="hr_qual_division_seq",sequenceName="hr_qual_division_seq")//(generate=GeneratorType.IDENTITY)
			private Long id;
			
		  /*  private String  code;
			private String ardesc;
			private String endesc;
			private Boolean isLast;
			private String longCode;
			*/
			@ManyToOne
			@JoinColumn (name="parent")
			private  HRQualificationDivision parent;
			
			@ManyToOne
			@JoinColumn (name="divisionLevel")
			private HRQualificationLevel divisionLevel;
			
			@OneToMany (mappedBy="parent",cascade=CascadeType.ALL)
	     private List <HRQualificationDivision> childs;
	     
			public String getEntityDisplayName() {
				return this.getId().toString();
			}
			/*@Transient
			private Integer order;
			
			
			public String toString() {
				 return new ToStringBuilder(this)
				 .append("ardesc", this.ardesc)
				 .append("endesc", this.endesc)
				 .toString();
			}
			public boolean equals(Object o) {
				 if (o == this) {
				 return true;
				 }
				 if (!(o instanceof HRQualificationDivision)) {
				 return false;
				 }
				 HRQualificationDivision rhs = (HRQualificationDivision) o;
				 return new EqualsBuilder()
				 .append(this.ardesc, rhs.getArdesc())
				 .append(this.endesc, rhs.getEndesc())
				 .isEquals();
			}*/
			public int hashCode() {
				return new HashCodeBuilder(991383961, 1226766147)
				.append(this.getId())

				.toHashCode();
			}
			
			
			/*@Transient
			public String getNameForCertainLocale(){
				LocaleUtil localeUtil = LocaleUtil.getInstance();
				if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
					return getEndesc();
				}
				return getEndesc();
			}*/
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			/*public String getCode() {
				return code;
			}
			public void setCode(String code) {
				this.code = code;
			}
			public String getArdesc() {
				return ardesc;
			}
			public void setArdesc(String ardesc) {
				this.ardesc = ardesc;
			}
			public String getEndesc() {
				return endesc;
			}
			public void setEndesc(String endesc) {
				this.endesc = endesc;
			}
			public Boolean getIsLast() {
				return isLast;
			}
			public void setIsLast(Boolean isLast) {
				this.isLast = isLast;
			}*/
			public HRQualificationDivision getParent() {
				return parent;
			}
			public void setParent(HRQualificationDivision parent) {
				this.parent = parent;
			}
			public HRQualificationLevel getDivisionLevel() {
				return divisionLevel;
			}
			public void setDivisionLevel(HRQualificationLevel divisionLevel) {
				this.divisionLevel = divisionLevel;
			}
			public List<HRQualificationDivision> getChilds() {
				return childs;
			}
			public void setChilds(List<HRQualificationDivision> childs) {
				this.childs = childs;
			}
			
			
			
			/*public String getLongCode() {
				return longCode;
			}
			public void setLongCode(String longCode) {
				this.longCode = longCode;
			}
			public Integer getOrder() {
				return order;
			}
			public void setOrder(Integer order) {
				this.order = order;
			}*/
			

	}


