package com.rajputLife.repository.security;

import com.rajputLife.entity.security.UsersRoles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRolesRepository extends CrudRepository<UsersRoles, Long> {

	@Query("SELECT u.role_id FROM UsersRoles u WHERE u.user_id = :user_id")
	public Set<String> getRolesByUserId(@Param("user_id") Integer user_id);
}
