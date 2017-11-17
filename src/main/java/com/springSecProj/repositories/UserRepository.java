package com.springSecProj.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springSecProj.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
	@Query(value="SELECT COUNT(*) FROM users_roles ur WHERE ur.role_id = 2", nativeQuery=true)
	int getCountOfAdminRoleUser();
}
