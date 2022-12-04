package com._4s_.requestsApproval.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


@Entity//(access=AccessType.FIELD)
@Table(name="dbconnections")
public class DBConnections implements Auditable,Serializable {
	
	
	
			@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dbconnections_seq")
			@SequenceGenerator(name="dbconnections_seq",sequenceName="dbconnections_seq")
			private Long id;
		    private String description;
			private String host_name;
			private String service_id;
			private String user_name;
			private String db_pass;
			
			private Integer is_active;
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getHost_name() {
				return host_name;
			}
			public void setHost_name(String host_name) {
				this.host_name = host_name;
			}
			public String getService_id() {
				return service_id;
			}
			public void setService_id(String service_id) {
				this.service_id = service_id;
			}
			public String getUser_name() {
				return user_name;
			}
			public void setUser_name(String user_name) {
				this.user_name = user_name;
			}
			public String getDb_pass() {
				return db_pass;
			}
			public void setDb_pass(String db_pass) {
				this.db_pass = db_pass;
			}
			public Integer getIs_active() {
				return is_active;
			}
			public void setIs_active(Integer is_active) {
				this.is_active = is_active;
			}
			public String getEntityDisplayName() {
				// TODO Auto-generated method stub
				return null;
			}
			

}
