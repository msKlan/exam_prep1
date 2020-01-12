/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Klan
 */
public class AddressesDTO {

    List<AddressDTO> all = new ArrayList<>();

    public AddressesDTO(List<Address> addressEntities) {
        for (Address addressEntity : addressEntities) {
            all.add(new AddressDTO(addressEntity));
        }
    }

    public List<AddressDTO> getAll() {
        return all;
    }

    public void setAll(List<AddressDTO> all) {
        this.all = all;
    }
    
    

}
