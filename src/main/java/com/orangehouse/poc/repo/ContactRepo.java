package com.orangehouse.poc.repo;

import com.orangehouse.poc.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContactRepo extends JpaRepository<Contact, String>
{
    @Query("SELECT con FROM Contact con WHERE con.email = ?1")
    Optional<Contact> findByEmail(String email);
}
