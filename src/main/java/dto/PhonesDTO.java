package dto;

import entities.Phone;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Klan
 */
public class PhonesDTO {
    List<PhoneDTO> all = new ArrayList<>();
   
    public PhonesDTO(List<Phone> phoneEntities) {
        for (Phone phoneEntity : phoneEntities ){
            all.add(new PhoneDTO(phoneEntity));
        }
    }

    public List<PhoneDTO> getAll() {
        return all;
    }

    public void setAll(List<PhoneDTO> all) {
        this.all = all;
    }
   
}