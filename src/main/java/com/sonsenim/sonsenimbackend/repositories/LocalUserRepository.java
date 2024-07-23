package com.sonsenim.sonsenimbackend.repositories;

import com.sonsenim.sonsenimbackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
}