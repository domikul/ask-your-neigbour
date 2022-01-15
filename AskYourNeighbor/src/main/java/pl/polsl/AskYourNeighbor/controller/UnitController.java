package pl.polsl.AskYourNeighbor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.AskYourNeighbor.model.dao.UnitDao;
import pl.polsl.AskYourNeighbor.service.UnitService;

import java.util.List;

import static pl.polsl.AskYourNeighbor.constant.ResourceUrl.UNITS;

@RestController
@RequestMapping(UNITS)
@CrossOrigin(origins = "http://localhost:4200")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UnitDao> getAllUnits() {
        return unitService.getAllUnits();
    }

    @GetMapping(value = "/{idUnit}")
    @ResponseStatus(HttpStatus.OK)
    public UnitDao getUnitById(@PathVariable Long idUnit) {
        return unitService.findUnitById(idUnit);
    }
}
