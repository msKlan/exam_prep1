package dto;

import entities.CityInfo;
import java.util.Objects;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class CityInfoDTO {
    private Integer id;
    private String zipCode;
    private String city;

    public CityInfoDTO() {
    }

    public CityInfoDTO(CityInfo cityinfo) {
        this.id = cityinfo.getId();
        this.zipCode = cityinfo.getZipCode();
        this.city = cityinfo.getCity();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.zipCode);
        hash = 59 * hash + Objects.hashCode(this.city);
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
        final CityInfoDTO other = (CityInfoDTO) obj;
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
