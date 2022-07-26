package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="hr_effect_equation")
public class HREffectEquation implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_EFFECT_EQUATION_SEQ")
	@SequenceGenerator(name="HR_EFFECT_EQUATION_SEQ",sequenceName="HR_EFFECT_EQUATION_SEQ")//(generate=GeneratorType.IDENTITY)
	private Long id ;
	
	@ManyToOne
	@JoinColumn (name="effect")
	private HREffect effect ;
	private Long rank;
	private String ttype;
	private String entry ;
	private String flag;
  
	public String getEntityDisplayName() {
		return "EffectEquation Addition";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.effect)
		 .append("ename", this.flag)
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREffectEquation)) {
		 return false;
		 }
		 HREffectEquation rhs = (HREffectEquation) o;
		 return new EqualsBuilder()
		 .append(this.effect, rhs.getEffect())
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())

		.toHashCode();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public HREffect getEffect() {
		return effect;
	}
	public void setEffect(HREffect effect) {
		this.effect = effect;
	}
	public Long getRank() {
		return rank;
	}
	public void setRank(Long rank) {
		this.rank = rank;
	}
	public String getTtype() {
		return ttype;
	}
	public void setTtype(String ttype) {
		this.ttype = ttype;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
