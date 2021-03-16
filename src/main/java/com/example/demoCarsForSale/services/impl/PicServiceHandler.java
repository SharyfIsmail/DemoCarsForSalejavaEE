package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.impl.AdDaoImpl;
import com.example.demoCarsForSale.dao.impl.PicDaoImpl;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.services.PicService;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class PicServiceHandler extends AbstractService implements PicService {
    private static final PicDao PIC_DAO = new PicDaoImpl();
    private static final AdDao AD_DAO = new AdDaoImpl();

    @Override
    public List<Pic> save(List<Pic> pics) {
        try {

            return PIC_DAO.save(pics);
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Pic> get(Serializable adId) {
        try {

            return PIC_DAO.get(adId);
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong while fetching Pics", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(Ad ad) {
        try {
            PIC_DAO.delete(ad);
            AD_DAO.updateEditDate(ad.getAdId());
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
