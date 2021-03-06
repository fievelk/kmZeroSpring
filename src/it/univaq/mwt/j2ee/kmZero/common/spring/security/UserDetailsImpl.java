package it.univaq.mwt.j2ee.kmZero.common.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import it.univaq.mwt.j2ee.kmZero.business.model.Role;
import it.univaq.mwt.j2ee.kmZero.business.model.Seller;
import it.univaq.mwt.j2ee.kmZero.business.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private User user;

	public UserDetailsImpl(User user) {
		super();
		this.user = user;
	}

	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			result.add(new GrantedAuthorityImpl(role.getName()));
		}
		return result;
	}

	@Override
	public String getPassword() {
		return user.getPassword().getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	/*---- USER DATA TO DISPLAY WITH PRINCIPAL -- START -----*/
	public long getId(){
    	return user.getId();
    }
    public String getName() {
        return user.getName();
    }
    public String getSurname() {
        return user.getSurname();
    }
    public String getEmail() {
        return user.getEmail();
    }
    public String getAddress() {
        return user.getAddress();
    }
    public String getP_Iva(){
    	Seller s = (Seller)user;
    	return s.getP_iva();
    }
    public String getCompany(){
    	Seller s = (Seller)user;
    	return s.getCompany();
    }
    public Set<Role> getRoles() {
        return user.getRoles();
    }
    public boolean isAdmin(){
    	return user.getRoles().contains(new Role("admin"));
    }
    public boolean isUser(){
    	return user.getRoles().contains(new Role("user"));
    }
    public boolean isSeller(){
    	return user.getRoles().contains(new Role("seller"));
    }
    /*---- USER DATA TO DISPLAY WITH PRINCIPAL -- END -----*/
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [username=" + user.getEmail() + "]";
	}

	public User getUser() {
		return user;
	}

}
