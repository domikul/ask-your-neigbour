package pl.polsl.AskYourNeighbor.service;

import org.springframework.stereotype.Service;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.dao.UnitDao;
import pl.polsl.AskYourNeighbor.repository.UnitRepository;
import pl.polsl.AskYourNeighbor.constant.ExceptionMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<UnitDao> getAllUnits() {
        return unitRepository.findAll().stream()
                .map(UnitDao::new)
                .collect(Collectors.toList());
    }

    @Override
    public UnitDao findUnitById(Long idUnit) {
        return unitRepository.findById(idUnit).map(UnitDao::new).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));
    }

}
