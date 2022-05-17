package com.abnamro.assignment.recipemanager.controller;

import com.abnamro.assignment.api.UnitManagementApi;
import com.abnamro.assignment.model.UnitDetail;
import com.abnamro.assignment.recipemanager.service.UnitManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UnitController implements UnitManagementApi {
    private final UnitManager unitManager;

    @Override
    public ResponseEntity<UnitDetail> addScaleUnits(UnitDetail unitDetails) {
        return new ResponseEntity<>(unitManager.addUnit(unitDetails), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UnitDetail>> fetchAllScaleUnits() {

        return new ResponseEntity<>(unitManager.getAllUnit(),HttpStatus.OK);
    }
}
