package ml.simplecontactsapi;

import javax.persistence.*;
import java.time.LocalDate;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

@Entity
@Cacheable
@Table(name = "contacts")
public class Contact extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_prefix")
    private String namePrefix;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "name_suffix")
    private String nameSuffix;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "company")
    private String company;

    @Column(name = "department")
    private String department;

    @Column(name = "title")
    private String title;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "website")
    private String website;

    @Column(name = "notes")
    private String notes;

    @Column(name = "label")
    private String label;

    public Long getId() {
        return id;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCompany() {
        return company;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getWebsite() {
        return website;
    }

    public String getNotes() {
        return notes;
    }

    public String getLabel() {
        return label;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("\nId = %d", id));
        sb.append(String.format("\nPrefix = %s", namePrefix == null ? "null" : namePrefix));
        sb.append(String.format("\nFirst Name = %s", firstName == null ? "null" : firstName));
        sb.append(String.format("\nMiddle Name = %s", middleName == null ? "null" : middleName));
        sb.append(String.format("\nLast Name = %s", last_name == null ? "null" : last_name));
        sb.append(String.format("\nSuffix = %s", nameSuffix == null ? "null" : nameSuffix));
        sb.append(String.format("\nNickname = %s", nickname == null ? "null" : nickname));
        sb.append(String.format("\nCompany = %s", company == null ? "null" : company));
        sb.append(String.format("\nDepartment = %s", department == null ? "null" : department));
        sb.append(String.format("\nTitle = %s", title == null ? "null" : title));
        sb.append(String.format("\nPhone = %s", phone == null ? "null" : phone));
        sb.append(String.format("\nEmail = %s", email == null ? "null" : email));
        sb.append(String.format("\nDOB = %s", dateOfBirth == null ? "null" : dateOfBirth));
        sb.append(String.format("\nWebsite = %s", website == null ? "null" : website));
        sb.append(String.format("\nNotes = %s", notes == null ? "null" : notes));
        sb.append(String.format("\nLabel = %s", label == null ? "null" : label));
        return sb.toString();
    }
}
