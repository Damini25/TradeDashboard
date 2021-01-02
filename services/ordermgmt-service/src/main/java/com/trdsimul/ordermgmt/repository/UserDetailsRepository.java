package com.trdsimul.ordermgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.ordermgmt.model.entity.UserDetails;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Integer> {

	@Query("select ud from UserDetails ud where UPPER(ud.userEmail) = UPPER(:userName) and UPPER(ud.password) = UPPER(:password)")
	Optional<UserDetails> loginUser( @Param("userName") String userName, @Param("password") String password);

}
