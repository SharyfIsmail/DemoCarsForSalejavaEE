package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pagination;
import com.example.demoCarsForSale.dao.model.Pic;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class AdDaoImpl extends AbstractDao implements AdDao {

    @Override
    public List<Pagination> getRecords(int start, int total) {
        EntityManager entityManager = entityManager();

        return entityManager.createQuery("SELECT NEW com.example.demoCarsForSale.dao.model.Pagination(" +
            " ad.id," +
            " ad.year," +
            " ad.brand," +
            " ad.model," +
            " ad.condition," +
            " ad.user.name," +
            " ad.pics.size," +
            " ad.createDate)" +
            " FROM Ad ad" +
            " LEFT JOIN ad.user" +
            " LEFT JOIN ad.pics", Pagination.class)
            .setFirstResult((start - 1) * total)
            .setMaxResults(total)
            .getResultList();
    }

    @Override
    public void deletePicFromAd(Pic pic) {
        EntityManager entityManager = entityManager();
        long adId = pic.getAd().getAdId();

        Ad ad = entityManager.createQuery("SELECT ad FROM Ad ad" +
            " LEFT JOIN FETCH ad.pics WHERE ad.id=:adId", Ad.class)
            .setParameter("adId", adId)
            .getSingleResult();

        ad.removePicFromAd(pic);
        ad.setEditDate(LocalDateTime.now());
        update(ad);
    }

    public Ad getDetailedInfoAboutAd(long id) {
        EntityManager entityManager = entityManager();

        return entityManager.createQuery("SELECT ad FROM Ad ad" +
            " LEFT JOIN FETCH ad.pics" +
            " WHERE ad.id =:id", getClassType())
            .setParameter("id", id)
            .getSingleResult();
    }

    @Override
    public Class<Ad> getClassType() {
        return Ad.class;
    }
}
