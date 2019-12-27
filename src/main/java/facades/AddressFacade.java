package facades;

import dto.AddressDTO;
import dto.AddressesDTO;
import entities.Address;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 * @author Renz Rename Class to a relevant name Add add relevant facade methods
 */
public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private AddressFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
//det er her vi persister vores address. 

    public AddressDTO addAddress(AddressDTO ad) {
        EntityManager em = emf.createEntityManager();
        Address address = new Address(ad.getStreet(), ad.getAdditionalInfo());
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AddressDTO(address);
    }
//denne metode henter en addresse ud fra et id som den får som parameter

    public AddressDTO getAddress(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            Address ad = em.find(Address.class, id);
            return new AddressDTO(ad);
        } finally {
            em.close();
        }
    }

//her henter vi alle addresserne
    public AddressesDTO getAllAddress() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Address> list = em.createQuery("SELECT a FROM Address a", Address.class).getResultList();
            return new AddressesDTO(list);
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) {

        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/CA2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        AddressFacade af = getAddressFacade(EMF);
        System.out.println(af.getAddress(1).getStreet());
    }
//denne metoder tager imod en addresse som skal redigeres. 

    public AddressDTO editAddress(AddressDTO a) {
        EntityManager em = emf.createEntityManager();
        Address address = new Address(a.getAdditionalInfo(), a.getStreet());
        address.setId(a.getId());
        try {
            em.getTransaction().begin();
            em.merge(address);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return new AddressDTO(address);
    }
//fjerne en addresse udfra id'et.

    public AddressDTO removeAddress(int id) {
        EntityManager em = emf.createEntityManager();
        Address address = em.find(Address.class, id);
        try {
            em.getTransaction().begin();
            em.remove(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AddressDTO(address);
    }

}
