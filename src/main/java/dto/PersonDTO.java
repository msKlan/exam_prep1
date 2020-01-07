/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import entities.Hobby;
import entities.Person;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author vince
 */
@Schema(name = "PersonInfo")

public class PersonDTO {

//    private int id;
//    @Schema(required = true, example = "Hans")
//    private String fName;
//    @Schema(required = true, example = "Hansen")
//    private String lName;
//    @Schema(required = true, example = "hans@hansen.com")
//    private String email;
//    @Schema(example = "[\"Street 10\",\"Apartment\"]")
//    private Address address;
//    @Schema(example = "[\"80808080\",\"Private\"]")
//    private List<Phone> phones;
//     @Schema(example="[\"Football\",\"Sport\"]")
//    private List<Hobby> hobbies;
    private int id;
    private String fName;
    private String lName;
    private String email;

    public PersonDTO(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
        }
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.email = person.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        final PersonDTO other = (PersonDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

}
