package com.abnamro.assignment.recipemanager.repository;

import com.abnamro.assignment.recipemanager.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile,String> {
}
