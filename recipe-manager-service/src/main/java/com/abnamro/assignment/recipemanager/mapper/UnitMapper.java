package com.abnamro.assignment.recipemanager.mapper;

import com.abnamro.assignment.model.UnitDetail;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnitMapper {
    UnitMapper UNIT_MAPPER = Mappers.getMapper(UnitMapper.class);

    ScaleUnit mapToScaleUnit(UnitDetail unitDetails);

    UnitDetail mapToUnitDetails(ScaleUnit ingredient);

    List<UnitDetail> mapToUnitDetails(List<ScaleUnit> units);
}
