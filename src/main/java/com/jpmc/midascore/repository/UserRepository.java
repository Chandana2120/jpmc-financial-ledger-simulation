package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserRecord, Long> {

    // Finds a user by their primary key ID
    UserRecord findById(long id);

    // This is required for Task 3 to find the specific user "waldorf"
    UserRecord findByName(String name);
}