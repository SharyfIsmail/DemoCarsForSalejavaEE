package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.model.Pic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("picDao")
public class PicDaoImpl extends AbstractDao<Pic> implements PicDao {

    @Override
    public List<Pic> savePics(List<Pic> pics) {
        EntityManager entityManager = getEntityManager();
        pics.forEach(entityManager::persist);

        return pics;
    }

    @Override
    public Pic getByIdWithAd(long id) {
        EntityManager entityManager = getEntityManager();

        return entityManager.createQuery("SELECT pic FROM Pic pic" +
            " LEFT JOIN FETCH  pic.ad" +
            " WHERE pic.picId =:id", Pic.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    @Override
    public Class<Pic> getClassType() {
        return Pic.class;
    }
}
