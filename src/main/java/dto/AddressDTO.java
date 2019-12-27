/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import entities.CityInfo;
import entities.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Renz
 */
public class AddressDTO {

    @Schema(name = "AddressInfo")
    private int id;
    private String street;
    private String additionalInfo;
    private List<PersonDTO> persons = new ArrayList();
    private CityInfoDTO cityInfo;

    public AddressDTO(Address address) {
        if (address.getId() != null) {
            this.id = address.getId();
        }
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();

            for (Person person : address.getPersons()) {
                this.persons.add(new PersonDTO(person));
            }
        
        if (address.getCityInfo() != null) {
            this.cityInfo = new CityInfoDTO(address.getCityInfo());
        }

    }

    public AddressDTO() {

    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public List<PersonDTO> getPerson() {
        return persons;
    }

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setPerson(List<PersonDTO> person) {
        this.persons = persons;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.street);
        hash = 67 * hash + Objects.hashCode(this.additionalInfo);
        hash = 67 * hash + Objects.hashCode(this.persons);
        hash = 67 * hash + Objects.hashCode(this.cityInfo);
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
        final AddressDTO other = (AddressDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.additionalInfo, other.additionalInfo)) {
            return false;
        }
        if (!Objects.equals(this.persons, other.persons)) {
            return false;
        }
        if (!Objects.equals(this.cityInfo, other.cityInfo)) {
            return false;
        }
        return true;
    }

}
