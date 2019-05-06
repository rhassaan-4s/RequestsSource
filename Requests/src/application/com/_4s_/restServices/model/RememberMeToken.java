package com._4s_.restServices.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="PERSISTENT_LOGINS")
public class RememberMeToken  implements Auditable,Serializable {
 
    private String username;
 
//    @Indexed
    private String series;
 
    private String tokenValue;
 
    private Date date;
 
    public RememberMeToken(){
 
    }
 

    public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}


	public RememberMeToken(PersistentRememberMeToken token){
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.tokenValue = token.getTokenValue();
        this.date = token.getDate();
    }
 
}
