package facades;

import dto.CityInfoDTO;
import dto.CityInfosDTO;
import entities.CityInfo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jacobfolkehildebrandt
 */
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfoDTO getCityInfo(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            CityInfo cityinfo = em.find(CityInfo.class, id);
            return new CityInfoDTO(cityinfo);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO addCityInfo(CityInfoDTO c) {
        EntityManager em = emf.createEntityManager();
        CityInfo cityinfo = new CityInfo(c.getZipCode(),c.getCity());
        try {
            em.getTransaction().begin();
            em.persist(cityinfo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CityInfoDTO(cityinfo);
    }

    public CityInfosDTO getAllCityInfo() {
        EntityManager em = emf.createEntityManager();
        List<CityInfo> list = em.createQuery("SELECT ci FROM CityInfo ci", CityInfo.class).getResultList();
        try {
            
            return new CityInfosDTO(list);
        } finally {
            em.close();
        }
    }

    public CityInfoDTO removeCityInfo(int id) {
        EntityManager em = emf.createEntityManager();
        CityInfo cityinfo = em.find(CityInfo.class, id);
        try {
            em.getTransaction().begin();
            em.remove(cityinfo);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CityInfoDTO(cityinfo);
    }

    public CityInfoDTO editCityInfo(CityInfoDTO c) {
        EntityManager em = emf.createEntityManager();
        
        CityInfo cityinfo = new CityInfo(c.getZipCode(),c.getCity());
        cityinfo.setId(c.getId());
        try {
            em.getTransaction().begin();
            em.merge(cityinfo);
            em.getTransaction().commit();

        } finally {
            em.close();

            return new CityInfoDTO(cityinfo);
        }
    }

}
