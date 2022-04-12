package ml.simplecontactsapi;

import javax.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String lastName;

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

    public String getLastName() {
        return lastName;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void updateContact(Contact c) {
        namePrefix = c.namePrefix;
        firstName = c.firstName;
        middleName = c.middleName;
        lastName = c.lastName;
        nameSuffix = c.nameSuffix;
        nickname = c.nickname;
        company = c.company;
        department = c.department;
        title = c.title;
        phone = c.phone;
        email = c.email;
        dateOfBirth = c.dateOfBirth;
        website = c.website;
        notes = c.notes;
        label = c.label;
    }

    @JsonIgnore
    public String getFullName() {
        StringBuilder sb = new StringBuilder();

        if (firstName != null) {
            sb.append(firstName);
        }

        if (middleName != null) {
            sb.append(" " + middleName);
        }

        if (lastName != null) {
            sb.append(" " + lastName);
        }

        // to handle null if no name is provided
        if (sb.length() == 0) {
            return "";
        }

        else {
            return sb.toString();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("\nId = %d", id));
        sb.append(String.format("\nPrefix = %s", namePrefix == null ? "null" : namePrefix));
        sb.append(String.format("\nFirst Name = %s", firstName == null ? "null" : firstName));
        sb.append(String.format("\nMiddle Name = %s", middleName == null ? "null" : middleName));
        sb.append(String.format("\nLast Name = %s", lastName == null ? "null" : lastName));
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
