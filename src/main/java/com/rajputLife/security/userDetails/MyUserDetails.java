package com.rajputLife.security.userDetails;

import com.rajputLife.entity.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetails implements UserDetails {

	private User user;
	
	public MyUserDetails(User user) {
		this.user = user;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<Role> roles = user.getRoles();
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//		for (Role role : roles) {
//			System.out.println("Role: "+role.getName() + " id: "+role.getId());
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//			System.out.println("SimpleGrantedAuthority: "+new SimpleGrantedAuthority(role.getName()).getAuthority() );
//		}
//
//		return authorities;
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<String> roles = user.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (String role : roles) {
//			System.out.println("Role: "+role.getName() + " id: "+role.getId());
			System.out.println("Role: "+role);
			authorities.add(new SimpleGrantedAuthority(role));
//			System.out.println("SimpleGrantedAuthority: "+new SimpleGrantedAuthority(role.getName()).getAuthority() );
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

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
		return user.isEnabled();
	}

}
