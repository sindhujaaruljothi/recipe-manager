package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.model.UnitDetail;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import com.abnamro.assignment.recipemanager.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.abnamro.assignment.recipemanager.mapper.IngredientMapper.INGREDIENT_MAPPER;
import static com.abnamro.assignment.recipemanager.mapper.UnitMapper.UNIT_MAPPER;

@Service
@RequiredArgsConstructor
public class UnitManager {
    private final UnitRepository unitRepository;

    public UnitDetail addUnit(UnitDetail unitDetails) {
        ScaleUnit scaleUnit = unitRepository.findByUnitValue(unitDetails.getUnitValue());
        if (Objects.isNull(scaleUnit)) {
            scaleUnit = unitRepository.save(UNIT_MAPPER.mapToScaleUnit(unitDetails));
        }
        return UNIT_MAPPER.mapToUnitDetails(scaleUnit);
    }

    public List<UnitDetail> getAllUnit() {
        List<ScaleUnit> unit = unitRepository.findAll();
        return UNIT_MAPPER.mapToUnitDetails(unit);
    }
}

