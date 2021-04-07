package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.exceptions.ForbiddenActionException;
import com.example.demoCarsForSale.services.PicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("picService")
@RequiredArgsConstructor
public class PicServiceImpl implements PicService {
    private final PicDao picDao;
    private final AdDao adDao;

    @Override
    public void delete(long id, long userId) {
        Pic picToDelete = picDao.existsById(id) ?
            picDao.getByIdWithAd(id) : null;

        if (picToDelete != null && isAbleToDelete(userId, picToDelete.getAd().getUser().getUserId())) {
            adDao.deletePicFromAd(picToDelete);
        } else {
            throw new ForbiddenActionException("Permission denied");
        }
    }
}
