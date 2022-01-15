package pl.polsl.AskYourNeighbor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.dao.FormDao;
import pl.polsl.AskYourNeighbor.service.AdminAccountService;
import pl.polsl.AskYourNeighbor.service.FormService;

import java.security.Principal;
import java.util.List;

import static pl.polsl.AskYourNeighbor.constant.ResourceUrl.*;

@RestController
@RequestMapping(FORMS)
@CrossOrigin(origins = "http://localhost:4200")
public class FormController {

    private final FormService formService;
    private final AdminAccountService adminAccountService;

    public FormController(FormService formService, AdminAccountService adminAccountService) {
        this.formService = formService;
        this.adminAccountService = adminAccountService;
    }

    @GetMapping(ACTIVE)
    @ResponseStatus(HttpStatus.OK)
    public List<FormDao> getActiveForms() {
        return formService.getAllActiveForms();
    }

    @GetMapping(QUEUE)
    @ResponseStatus(HttpStatus.OK)
    public List<FormDao> getFormsToReview() {
        return formService.getFormsQueue();
    }

    @GetMapping(value = "/{idForm}")
    @ResponseStatus(HttpStatus.OK)
    public FormDao getFormById(@PathVariable Long idForm) {
        return formService.findFormById(idForm);
    }

    @PatchMapping(value = "/{idForm}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptForm(Principal principal, @PathVariable Long idForm) {
        AdminAccount acceptingPerson = adminAccountService.findUserByPrincipal(principal);
        formService.acceptForm(idForm, acceptingPerson);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewForm(@RequestBody FormDao formDao) {
        formService.addNewForm(formDao);
    }

    @DeleteMapping(value = "/{idForm}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForm(@PathVariable Long idForm) {
        formService.deleteFormById(idForm);
    }
}
