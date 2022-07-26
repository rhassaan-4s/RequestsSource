package com._4s_.HR.model;

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

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.common.model.TreeDivisions;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_internal_division")
public class HRInternalDivision extends TreeDivisions {
		
		public HRInternalDivision() {
		// TODO Auto-generated constructor stub
	}
		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_internal_division_seq")
		@SequenceGenerator(name="hr_internal_division_seq",sequenceName="hr_internal_division_seq")//(generate=GeneratorType.IDENTITY)
		private Long id;
	   /* private String  code;
		private String ardesc;
		private String endesc;
		private Boolean isLast;
		private String longCode;
	*/	
		@ManyToOne
		@JoinColumn (name="parent")
		private  HRInternalDivision parent;
		
		@ManyToOne
		@JoinColumn (name="divisionLevel")
		private HRInternalLevel divisionLevel;
		
		
		@OneToMany (mappedBy="parent",cascade=CascadeType.ALL)
       private List  <HRInternalDivision> childs;
	   
		
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
			 if (!(o instanceof HRInternalDivision)) {
			 return false;
			 }
			 HRInternalDivision rhs = (HRInternalDivision) o;
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
		public HRInternalDivision getParent() {
			return parent;
		}
		public void setParent(HRInternalDivision parent) {
			this.parent = parent;
		}
		public HRInternalLevel getDivisionLevel() {
			return divisionLevel;
		}
		public void setDivisionLevel(HRInternalLevel divisionLevel) {
			this.divisionLevel = divisionLevel;
		}
		public List<HRInternalDivision> getChilds() {
			return childs;
		}
		public void setChilds(List<HRInternalDivision> childs) {
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
