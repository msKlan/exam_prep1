/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto_OUT;

import dto.HobbyDTO;
import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vince
 */
public class HobbiesDTO_OUT {
    List<HobbyDTO_OUT> all = new ArrayList<>();
    
    public HobbiesDTO_OUT(List<Hobby> hobbyEntities) {
        for (Hobby hobbyEntity : hobbyEntities ){
            all.add(new HobbyDTO_OUT(hobbyEntity));
        }
    }

    public List<HobbyDTO_OUT> getAll() {
        return all;
    }

    public void setAll(List<HobbyDTO_OUT> all) {
        this.all = all;
    }
    
    
}
