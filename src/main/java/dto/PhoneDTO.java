package dto;

import entities.Phone;
import entities.Person;
import java.util.Objects;

/**
 *
 * @author Klan
 */
public class PhoneDTO {

    private Integer id;
    private String number;
    private String description;
    private PersonDTO person;

    public PhoneDTO() {
    }

    public PhoneDTO(Phone phone) {
        if (phone.getId() != null) {
        this.id = phone.getId();
        }
        this.number = phone.getNumber();
        this.description = phone.getDescription();
        if (phone.getPerson() != null) {
            this.person = new PersonDTO(phone.getPerson());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.number);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.person);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhoneDTO other = (PhoneDTO) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }

        return true;
    }

}
