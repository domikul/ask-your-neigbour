package pl.polsl.AskYourNeighbor.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.AskYourNeighbor.exception.IncorrectRequestException;
import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.Category;
import pl.polsl.AskYourNeighbor.model.Form;
import pl.polsl.AskYourNeighbor.model.Unit;
import pl.polsl.AskYourNeighbor.model.dao.FormDao;
import pl.polsl.AskYourNeighbor.repository.CategoryRepository;
import pl.polsl.AskYourNeighbor.repository.FormRepository;
import pl.polsl.AskYourNeighbor.repository.UnitRepository;
import pl.polsl.AskYourNeighbor.constant.ExceptionMessage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;


    public FormServiceImpl(FormRepository formRepository, CategoryRepository categoryRepository, UnitRepository unitRepository) {
        this.formRepository = formRepository;
        this.categoryRepository = categoryRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public List<FormDao> getAllActiveForms() {
        return formRepository.findAllByAvailabilityStartIsNotNull()
                .stream()
                .map(FormDao::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<FormDao> getFormsQueue() {
        return formRepository.findAllByAvailabilityStartIsNull()
                .stream()
                .map(FormDao::new)
                .collect(Collectors.toList());
    }

    @Override
    public void addNewForm(FormDao formDao) {
        Form form = createAndFillForm(formDao);
        formRepository.save(form);
    }

    @Override
    public FormDao findFormById(Long idForm) {
        return formRepository.findById(idForm).map(FormDao::new).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));
    }

    @Override
    public void deleteFormById(Long idForm) {
        Form foundedRecord = formRepository.findById(idForm).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));

        formRepository.delete(foundedRecord);
    }

    @Override
    public void acceptForm(Long idForm, AdminAccount acceptingPerson) {
        Form foundedRecord = formRepository.findById(idForm).orElseThrow(() ->
                new IncorrectRequestException(ExceptionMessage.INCORRECT_REQUEST.getMessage()));

        foundedRecord.setAcceptingPerson(acceptingPerson);
        foundedRecord.setAvailabilityStart(LocalDate.now());
        formRepository.save(foundedRecord);
    }

    private Form createAndFillForm(FormDao formDao) {
        Form form = new Form();
        form.setNameOfApplicant(formDao.getNameOfApplicant());
        form.setCity(formDao.getCity());
        form.setStreet(formDao.getStreet());
        form.setNumber(formDao.getNumber());
        form.setPhoneNumber(formDao.getPhoneNumber());
        form.setProductName(formDao.getProductName());
        form.setDescription(formDao.getDescription());
        Category category = categoryRepository.findById(formDao.getIdCategory())
                .orElseThrow(() -> new IncorrectRequestException(ExceptionMessage.CATEGORY_DOES_NOT_EXIST.getMessage()));
        form.setCategory(category);
        form.setAmount(formDao.getAmount());
        Unit unit = unitRepository.findById(formDao.getIdUnit())
                .orElseThrow(() -> new IncorrectRequestException(ExceptionMessage.WRONG_UNIT.getMessage()));
        form.setUnit(unit);
        form.setPrice(formDao.getPrice());
        form.setAvailabilityEnd(formDao.getAvailabilityEnd());
        form.setSendDate(LocalDate.now());
        return form;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deleteOldForms() {
        formRepository.deleteAllByAvailabilityEndBefore(LocalDate.now());
    }
}
