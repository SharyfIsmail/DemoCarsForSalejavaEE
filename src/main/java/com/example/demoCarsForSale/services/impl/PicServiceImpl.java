package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.impl.AdDaoImpl;
import com.example.demoCarsForSale.dao.impl.PicDaoImpl;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.exceptions.ForbiddenActionException;
import com.example.demoCarsForSale.services.PicService;

import javax.servlet.http.HttpServletResponse;

public class PicServiceImpl extends AbstractService implements PicService {
    private static final PicDao PIC_DAO = new PicDaoImpl();
    private static final AdDao AD_DAO = new AdDaoImpl();

    @Override
    public void delete(long id, long userId) {
        startTransaction();
        Pic picToDelete = PIC_DAO.existsById(id) ?
            PIC_DAO.getByIdWithAd(id) : null;

        if (picToDelete != null && isAbleToDelete(userId, picToDelete.getAd().getUser().getId())) {
            AD_DAO.deletePicFromAd(picToDelete);

            closeTransaction();
        } else {
            rollback();
            throw new ForbiddenActionException("Permission denied", HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
