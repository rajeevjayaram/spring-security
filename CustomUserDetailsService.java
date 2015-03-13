package com.burndy.primefaces.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  /* 
    @Autowired
    private UserDAO userDAO;   
*/
    private UserDAOImpl userDAO = new UserDAOImpl();
    /*
    {
		
		@Override
		public com.burndy.primefaces.login.User getUser(String login) {
			// TODO Auto-generated method stub
			return null;
		}
	};   */

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
       
    	System.out.println("inside loadUserByUsername" + login + " getAuthorities  " + getAuthorities(1));
    	
    	com.burndy.primefaces.login.User  domainUser = userDAO.getUser(login);
    	System.out.println(  " Domain User    " + domainUser.getUsername() + " : " + domainUser.getPassword());

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(
        	domainUser.getUsername(),
        	domainUser.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            getAuthorities(1)
        );
    }
   
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
   
    public List<String> getRoles(Integer role) {

        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_MODERATOR");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        }
        return roles;
    }
   
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
