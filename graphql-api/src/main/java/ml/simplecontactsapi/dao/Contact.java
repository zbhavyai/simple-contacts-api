package ml.simplecontactsapi.dao;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.eclipse.microprofile.graphql.Input;
import org.eclipse.microprofile.graphql.Type;

@Type("Contact")
@Input("ContactInput")
@ApplicationScoped
public class Contact {
    private Long id;

    private String namePrefix;

    private String firstName;

    private String middleName;

    private String lastName;

    private String nameSuffix;

    private String nickname;

    private String company;

    private String department;

    private String title;

    private String phone;

    private String email;

    private LocalDate dateOfBirth;

    private String website;

    private String notes;

    private String label;

    public Contact() {
    }

    @JsonCreator
    public Contact(
            @JsonProperty("id") final Long id,
            @JsonProperty("namePrefix") final String namePrefix,
            @JsonProperty("firstName") final String firstName,
            @JsonProperty("middleName") final String middleName,
            @JsonProperty("lastName") final String lastName,
            @JsonProperty("nameSuffix") final String nameSuffix,
            @JsonProperty("nickname") final String nickname,
            @JsonProperty("company") final String company,
            @JsonProperty("department") final String department,
            @JsonProperty("title") final String title,
            @JsonProperty("phone") final String phone,
            @JsonProperty("email") final String email,
            @JsonProperty("dateOfBirth") final LocalDate dateOfBirth,
            @JsonProperty("website") final String website,
            @JsonProperty("notes") final String notes,
            @JsonProperty("label") final String label) {
        this.id = id;
        this.namePrefix = namePrefix;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nameSuffix = nameSuffix;
        this.nickname = nickname;
        this.company = company;
        this.department = department;
        this.title = title;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.website = website;
        this.notes = notes;
        this.label = label;
    }

    public Long getId() {
        return this.id;
    }

    public String getNamePrefix() {
        return this.namePrefix;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getNameSuffix() {
        return this.nameSuffix;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getCompany() {
        return this.company;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getLabel() {
        return this.label;
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
        this.namePrefix = c.namePrefix;
        this.firstName = c.firstName;
        this.middleName = c.middleName;
        this.lastName = c.lastName;
        this.nameSuffix = c.nameSuffix;
        this.nickname = c.nickname;
        this.company = c.company;
        this.department = c.department;
        this.title = c.title;
        this.phone = c.phone;
        this.email = c.email;
        this.dateOfBirth = c.dateOfBirth;
        this.website = c.website;
        this.notes = c.notes;
        this.label = c.label;
    }

    @JsonIgnore
    public String getFullName() {
        StringBuilder sb = new StringBuilder();

        if (this.firstName != null) {
            sb.append(this.firstName);
        }

        if (this.middleName != null) {
            sb.append(" " + this.middleName);
        }

        if (this.lastName != null) {
            sb.append(" " + this.lastName);
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof Contact)) {
            return false;
        }

        final Contact other = (Contact) obj;

        if (this.toString().equals(other.toString())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("\nId = %d", this.id));
        sb.append(String.format("\nPrefix = %s", this.namePrefix == null ? "null" : this.namePrefix));
        sb.append(String.format("\nFirst Name = %s", this.firstName == null ? "null" : this.firstName));
        sb.append(String.format("\nMiddle Name = %s", this.middleName == null ? "null" : this.middleName));
        sb.append(String.format("\nLast Name = %s", this.lastName == null ? "null" : this.lastName));
        sb.append(String.format("\nSuffix = %s", this.nameSuffix == null ? "null" : this.nameSuffix));
        sb.append(String.format("\nNickname = %s", this.nickname == null ? "null" : this.nickname));
        sb.append(String.format("\nCompany = %s", this.company == null ? "null" : this.company));
        sb.append(String.format("\nDepartment = %s", this.department == null ? "null" : this.department));
        sb.append(String.format("\nTitle = %s", this.title == null ? "null" : this.title));
        sb.append(String.format("\nPhone = %s", this.phone == null ? "null" : this.phone));
        sb.append(String.format("\nEmail = %s", this.email == null ? "null" : this.email));
        sb.append(String.format("\nDOB = %s", this.dateOfBirth == null ? "null" : this.dateOfBirth));
        sb.append(String.format("\nWebsite = %s", this.website == null ? "null" : this.website));
        sb.append(String.format("\nNotes = %s", this.notes == null ? "null" : this.notes));
        sb.append(String.format("\nLabel = %s", this.label == null ? "null" : this.label));
        return sb.toString();
    }
}
