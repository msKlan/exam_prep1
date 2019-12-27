/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto_OUT;

import dto.PersonDTO;
import entities.Phone;

/**
 *
 * @author vince
 */
public class PhoneDTO_OUT {

    private Integer id;
    private String number;
    private String description;

    public PhoneDTO_OUT() {
    }

    public PhoneDTO_OUT(Phone phone) {
        if (phone.getId() != null) {
            this.id = phone.getId();
        }
        this.number = phone.getNumber();
        this.description = phone.getDescription();
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
    
    
    

}
