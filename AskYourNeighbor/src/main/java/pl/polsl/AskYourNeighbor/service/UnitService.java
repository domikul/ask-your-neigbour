package pl.polsl.AskYourNeighbor.service;

import pl.polsl.AskYourNeighbor.model.dao.UnitDao;

import java.util.List;

public interface UnitService {

    List<UnitDao> getAllUnits();

    UnitDao findUnitById(Long idUnit);
}
