package com.rrm14.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rrm14.catalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
