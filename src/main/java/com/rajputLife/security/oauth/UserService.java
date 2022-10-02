package com.rajputLife.security.oauth;


import com.rajputLife.entity.security.User;
import com.rajputLife.entity.security.UsersRoles;
import com.rajputLife.enums.security.Provider;
import com.rajputLife.enums.security.Role;
import com.rajputLife.repository.security.RoleRepository;
import com.rajputLife.repository.security.UserRepository;
import com.rajputLife.repository.security.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	public User processOAuthPostLogin(String username) {
		User existUser = repo.getUserByUsername(username);
		
		if (existUser == null) {

			User newUser = new User();
			newUser.setUsername(username);
			newUser.setProvider(Provider.GOOGLE);
			newUser.setEnabled(true);
			repo.save(newUser);
			
			System.out.println("Created new user: " + username);

			UsersRoles usersRoles = new UsersRoles();
			int id = roleRepository.getRoleIdByRoleName(Role.USER.toString());
			usersRoles.setRole_id(id);
			usersRoles.setUser_id(newUser.getId());
			userRolesRepository.save(usersRoles);

			existUser = newUser;
			existUser.setRoles(Collections.singleton(Role.USER.toString()));
		}else{
			Set<String> roleNames = new HashSet<>();
			Set<String> roleIds = userRolesRepository.getRolesByUserId(existUser.getId());
			for (String roleId: roleIds) {
				roleNames.add(roleRepository.getRoleNameByRoleId(Integer.parseInt(roleId)));
			}
			existUser.setRoles(roleNames);
		}
		return existUser;
	}
}
