package com.rajputLife.security.userDetails;

import com.rajputLife.entity.security.User;
import com.rajputLife.repository.security.RoleRepository;
import com.rajputLife.repository.security.UserRepository;
import com.rajputLife.repository.security.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}else{
			Set<String> roleNames = new HashSet<>();
			Set<String> roleIds = userRolesRepository.getRolesByUserId(user.getId());
			for (String roleId: roleIds) {
				roleNames.add(roleRepository.getRoleNameByRoleId(Integer.parseInt(roleId)));
			}

			user.setRoles(roleNames);
		}
		
		return new MyUserDetails(user);
	}
}
