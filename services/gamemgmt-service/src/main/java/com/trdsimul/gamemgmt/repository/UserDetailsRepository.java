package com.trdsimul.gamemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.trdsimul.gamemgmt.model.entity.UserDetails;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Integer> {

	@Query("select ud from UserDetails ud where UPPER(ud.userEmail) = UPPER(:userName) and UPPER(ud.password) = UPPER(:password) ")
	Optional<UserDetails> loginUser( @Param("userName") String userName, @Param("password") String password);

	@Query("select ud from UserDetails ud where ud.userTypeId = :userTypeId")
	List<UserDetails> getBotUsers(@Param("userTypeId") Integer userTypeId);

}
