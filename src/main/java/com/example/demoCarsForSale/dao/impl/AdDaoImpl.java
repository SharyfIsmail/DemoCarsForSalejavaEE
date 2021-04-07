package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.web.dto.projection.AdShortInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository("adDao")
public class AdDaoImpl extends AbstractDao<Ad> implements AdDao {

    @Override
    public void deletePicFromAd(Pic pic) {
        EntityManager entityManager = getEntityManager();
        long adId = pic.getAd().getAdId();

        Ad ad = entityManager.createQuery("SELECT ad FROM Ad ad" +
            " LEFT JOIN FETCH ad.pics WHERE ad.id=:adId", Ad.class)
            .setParameter("adId", adId)
            .getSingleResult();

        ad.removePicFromAd(pic);
        ad.setEditDate(LocalDateTime.now());
        update(ad);
    }

    @Override
    public List<AdShortInfo> getRecords(int start, int total) {
        EntityManager entityManager =getEntityManager();

        return entityManager.createQuery("SELECT DISTINCT NEW com.example.demoCarsForSale.web.dto.projection.AdShortInfo(" +
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
            " LEFT JOIN ad.pics", AdShortInfo.class)
            .setFirstResult((start - 1) * total)
            .setMaxResults(total)
            .getResultList();
    }

    public Ad getDetailedInfoAboutAd(long id) {
        EntityManager entityManager =getEntityManager();

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
