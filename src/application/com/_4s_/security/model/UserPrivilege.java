package com._4s_.security.model; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
//import com._4s_.stores.model.Groupf;
//import com._4s_.stores.model.StoreTrnsDep;


@Entity
@Table (name = "security_user_privilege")
public class UserPrivilege implements Serializable,Auditable{
	public UserPrivilege() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="security_user_privilege_seq")
	@SequenceGenerator(name="security_user_privilege_seq",sequenceName="security_user_privilege_seq", allocationSize = 1)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")	
	private User user;
	@Column(name="add_basic")
	private boolean addBasic = false;
	@Column(name="add_trans")
	private boolean addTrans = false;
	@Column(name="update_basic")
	private boolean updateBasic = false;
	@Column(name="update_trans")
	private boolean updateTrans = false;
	@Column(name="del_basic")
	private boolean delBasic = false;
	@Column(name="del_trans")
	private boolean delTrans = false;
	@Column(name="edit_trans_number")
	private boolean editTransNumber = false;
	@Column(name="view_menu_specs")
	private boolean viewMenuSpecs = false;
	
	
	@Column(name="edit_branch")
	@NotNull(message = "commons.errors.requiredFields")
	private boolean editBranch = false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userprivilege")
	private List<UserBranch> userBranch = new ArrayList<UserBranch>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	public String toString() {
		return new ToStringBuilder(this).append("name", this.getId())
				.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof UserPrivilege)) {
			return false;
		}
		UserPrivilege rhs = (UserPrivilege) o;
		return new EqualsBuilder()
				.append(this.getId(), rhs.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(
				this.getId()).toHashCode();
	}
	public void setAddBasic(boolean addBasic) {
		this.addBasic = addBasic;
	}
	public boolean getAddBasic() {
		return addBasic;
	}
	public void setAddTrans(boolean addTrans) {
		this.addTrans = addTrans;
	}
	public boolean getAddTrans() {
		return addTrans;
	}
	public void setUpdateBasic(boolean updateBasic) {
		this.updateBasic = updateBasic;
	}
	public boolean getUpdateBasic() {
		return updateBasic;
	}
	public void setUpdateTrans(boolean updateTrans) {
		this.updateTrans = updateTrans;
	}
	public boolean getUpdateTrans() {
		return updateTrans;
	}
	public void setDelBasic(boolean delBasic) {
		this.delBasic = delBasic;
	}
	public boolean getDelBasic() {
		return delBasic;
	}
	public void setDelTrans(boolean delTrans) {
		this.delTrans = delTrans;
	}
	public boolean getDelTrans() {
		return delTrans;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setEditTransNumber(boolean editTransNumber) {
		this.editTransNumber = editTransNumber;
	}
	public boolean isEditTransNumber() {
		return editTransNumber;
	}
	public void setViewMenuSpecs(boolean viewMenuSpecs) {
		this.viewMenuSpecs = viewMenuSpecs;
	}
	public boolean isViewMenuSpecs() {
		return viewMenuSpecs;
	}
	public void setEditBranch(boolean editBranch) {
		this.editBranch = editBranch;
	}
	public boolean isEditBranch() {
		return editBranch;
	}
	public void setUserBranch(List<UserBranch> userBranch) {
		this.userBranch = userBranch;
	}
	public List<UserBranch> getUserBranch() {
		return userBranch;
	}

	
}
