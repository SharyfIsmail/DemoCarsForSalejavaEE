package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.model.Pic;

import javax.persistence.EntityManager;
import java.util.List;

public class PicDaoImpl extends AbstractDao implements PicDao {

    @Override
    public List<Pic> savePics(List<Pic> pics) {
        EntityManager entityManager = entityManager();
        pics.forEach(entityManager::persist);

        return pics;
    }

    @Override
    public Pic getByIdWithAd(long id) {
        EntityManager entityManager = entityManager();

        return entityManager.createQuery("SELECT pic FROM Pic pic" +
            " LEFT JOIN FETCH  pic.ad" +
            " WHERE pic.id =:id", Pic.class)
            .setParameter("id", id)
            .getSingleResult();
    }

    @Override
    public Class<Pic> getClassType() {
        return Pic.class;
    }
}
