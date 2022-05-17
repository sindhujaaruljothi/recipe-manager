package com.abnamro.assignment.recipemanager.repository;

import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<ScaleUnit,Long> {
    ScaleUnit findByUnitValue(String unitValue);
}
