package dto;

import entities.Hobby;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class HobbiesDTO {
     @Schema(example="[\"1\",\"Coins\",\"Collection\"]")
    List<HobbyDTO> all = new ArrayList<>();
    
    public HobbiesDTO(List<Hobby> hobbyEntities) {
        for (Hobby hobbyEntity : hobbyEntities ){
            all.add(new HobbyDTO(hobbyEntity));
        }
    }

    public List<HobbyDTO> getAll() {
        return all;
    }

    public void setAll(List<HobbyDTO> all) {
        this.all = all;
    }
    
    
}
