package pl.polsl.AskYourNeighbor.model.dao;

import pl.polsl.AskYourNeighbor.model.AdminAccount;
import pl.polsl.AskYourNeighbor.model.Form;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FormDao {

    private Long id;
    private String nameOfApplicant;
    private String city;
    private String street;
    private String number;
    private Long phoneNumber;
    private String productName;
    private String description;
    private Long idCategory;
    private Integer amount;
    private Long idUnit;
    private BigDecimal price;
    private LocalDate availabilityStart;
    private LocalDate availabilityEnd;
    private Long idAcceptingPerson;
    private LocalDate sendDate;

    public FormDao() {
    }

    public FormDao(Form form) {
        this.id = form.getId();
        this.nameOfApplicant = form.getNameOfApplicant();
        this.city = form.getCity();
        this.street = form.getStreet();
        this.number = form.getNumber();
        this.phoneNumber = form.getPhoneNumber();
        this.productName = form.getProductName();
        this.description = form.getDescription();
        this.idCategory = form.getCategory().getId();
        this.amount = form.getAmount();
        this.idUnit = form.getUnit().getId();
        this.price = form.getPrice();
        this.availabilityStart = form.getAvailabilityStart();
        this.availabilityEnd = form.getAvailabilityEnd();
        AdminAccount acceptingPerson = form.getAcceptingPerson();
        if (acceptingPerson != null) {
            this.idAcceptingPerson = acceptingPerson.getId();
        }
        this.sendDate = form.getSendDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfApplicant() {
        return nameOfApplicant;
    }

    public void setNameOfApplicant(String nameOfApplicant) {
        this.nameOfApplicant = nameOfApplicant;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(Long idUnit) {
        this.idUnit = idUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getAvailabilityStart() {
        return availabilityStart;
    }

    public void setAvailabilityStart(LocalDate availabilityStart) {
        this.availabilityStart = availabilityStart;
    }

    public LocalDate getAvailabilityEnd() {
        return availabilityEnd;
    }

    public void setAvailabilityEnd(LocalDate availabilityEnd) {
        this.availabilityEnd = availabilityEnd;
    }

    public Long getIdAcceptingPerson() {
        return idAcceptingPerson;
    }

    public void setIdAcceptingPerson(Long idAcceptingPerson) {
        this.idAcceptingPerson = idAcceptingPerson;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }
}
