package com.kupchyk.javaee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kupchyk.javaee.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}