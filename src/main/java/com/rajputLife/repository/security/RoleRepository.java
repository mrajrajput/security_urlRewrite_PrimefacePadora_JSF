package com.rajputLife.repository.security;

import com.rajputLife.entity.security.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	@Query("SELECT u.id FROM Role u WHERE u.name = :name")
	public Integer getRoleIdByRoleName(@Param("name") String name);

	@Query("SELECT u.name FROM Role u WHERE u.id = :id")
	public String getRoleNameByRoleId(@Param("id") Integer id);
}
