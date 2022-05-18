package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.recipemanager.TestDataLoader;
import com.abnamro.assignment.recipemanager.repository.UnitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitManagerTest {
    @InjectMocks
    private UnitManager unitManager;
    @Mock
    private UnitRepository unitRepository;

    @Test
    void test_getUnits_whenGetAllUnits_thenRetrieveAllUnits() {
        when(unitRepository.findAll()).thenReturn(TestDataLoader.getScaleUnitModel());
        Assertions.assertEquals(unitManager.getAllUnit().get(0).getUnitValue(), TestDataLoader.UNIT_VALUE);
        verify(unitRepository).findAll();
    }

    @Test
    void test_getUnits_whenNoValueInUnitRepo_thenReturnEmptyList() {
        when(unitRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(unitManager.getAllUnit().size(), 0);
        verify(unitRepository).findAll();
    }


}