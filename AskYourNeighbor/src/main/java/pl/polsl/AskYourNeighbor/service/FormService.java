package pl.polsl.AskYourNeighbor.service;

import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.dao.FormDao;

import java.util.List;

public interface FormService {

    List<FormDao> getAllActiveForms();

    List<FormDao> getFormsQueue();

    void addNewForm(FormDao formDao);

    FormDao findFormById(Long idForm);

    void deleteFormById(Long idForm);

    void acceptForm(Long idForm, AdminAccount acceptingPerson);
}
