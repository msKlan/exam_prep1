/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto_OUT;

import dto.AddressDTO;
import dto.HobbyDTO;

import entities.Hobby;
import entities.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Klan
 */
public class PersonDTO_OUT {
    private int id;
    private String fName;
    private String lName;
    private String email;
    private String phone;
    private AddressDTO_OUT address;
    private List<HobbyDTO_OUT> hobbies = new ArrayList<>();

    public PersonDTO_OUT() {
    }

    public PersonDTO_OUT(Person person) {
        if (person.getId() != null) {
            this.id = person.getId();
        }
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.email = person.getEmail();
        this.email = person.getPhone();
        
        if(person.getAddress() != null){
        this.address = new AddressDTO_OUT(person.getAddress());
        }
        

        if(person.getHobbies() != null){
        for (Hobby hobby : person.getHobbies()) {
        this.hobbies.add(new HobbyDTO_OUT(hobby));
        }
        }
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressDTO_OUT getAddress() {
        return address;
    }

    public void setAddress(AddressDTO_OUT address) {
        this.address = address;
    }

    public List<HobbyDTO_OUT> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO_OUT> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.fName);
        hash = 29 * hash + Objects.hashCode(this.lName);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.phone);
        hash = 29 * hash + Objects.hashCode(this.address);

        hash = 29 * hash + Objects.hashCode(this.hobbies);
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
        final PersonDTO_OUT other = (PersonDTO_OUT) obj;
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
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }

        if (!Objects.equals(this.hobbies, other.hobbies)) {
            return false;
        }
        return true;
    }
    
    
}
