package dto;

import entities.CityInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class CityInfosDTO {
    List<CityInfoDTO> all = new ArrayList<>();
    
    public CityInfosDTO(List<CityInfo> cityinfoEntities) {
        for (CityInfo cityinfoEntity : cityinfoEntities ){
            all.add(new CityInfoDTO(cityinfoEntity));
        }
    }

    public List<CityInfoDTO> getAll() {
        return all;
    }

    public void setAll(List<CityInfoDTO> all) {
        this.all = all;
    }
    
    
}
