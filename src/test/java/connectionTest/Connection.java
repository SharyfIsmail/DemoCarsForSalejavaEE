package connectionTest;

import com.example.demoCarsForSale.dao.model.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
    private EntityManager em;

    @Test
    @Order(1)
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistence");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        User user1 = em.find(User.class, (long) 3);
        user1.getAds().forEach(x -> System.out.println(x.getUser().getId()));
//        Ad ad = Ad.builder()
//            .editDate(new Timestamp(System.currentTimeMillis()))
//            .condition(Condition.NEW)
//            .createDate(new Timestamp(System.currentTimeMillis()))
//            .power(12)
//            .engineCapacity(21)
//            .mileage(12)
//            .brand("Cooper")
//            .year(12)
//            .model("s")
//            .user(user1)
//            .build();
//        user1.getAds().add(ad);

        // em.detach(user1);

        //    em.persist(user1);
        // em.remove(user1);
        //   System.out.println(user1);

        //       em.remove(user1);
        // em.createQuery(" FROM User ").getResultList().forEach(x -> System.out.println(x));
        em.getTransaction().commit();
        em.getEntityManagerFactory().close();
        em.close();
    }

    @Test
    @Order(2)
    public void close() {
    }
}

