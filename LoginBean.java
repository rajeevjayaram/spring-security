
package com.burndy.primefaces.login;


import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.burndy.spring.service.login.SpringLoginService;
import com.burndy.util.SpringContextSingleton;


@ManagedBean(name="loginBean")
@RequestScoped
public class LoginBean {

	public LoginBean () {}
	
	
	private String username =null;
	
	private String password = null;
	
	// @ManagedProperty(value="#{authenticationManager}")
    private AuthenticationManager authenticationManager = null;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		System.out.println("inside setUsername" + username  + "this.username" + this.username );
		if (this.username == null)
			this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		System.out.println("inside setPassword" + password  + "this.password" + this.password );
		if (this.password == null)

		this.password = password;
	}
	
	
	public AuthenticationManager getAuthenticationManager() {
		System.out.println("inside setAuthenticationManager" );
		
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		System.out.println("inside setAuthenticationManager" );
		
		this.authenticationManager = authenticationManager;
	}

public String doProcess () {

	System.out.println("inside doProcess before getting sproibg bean");
	
	System.out.println("inside doProcess" + username  + "? " + getUsername());

	
	System.out.println("inside doProcess" + password + "? " + getPassword());

	try {
        Authentication request = new UsernamePasswordAuthenticationToken(this.getUsername(), this.getPassword());
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
    } catch (AuthenticationException e) {
        e.printStackTrace();
        return "incorrect";
    }
	SpringLoginService springLogin = (SpringLoginService) SpringContextSingleton.retrieveSpringBean("loginService");
	return springLogin.doValidate(getUsername(), getPassword() );}

	
}
