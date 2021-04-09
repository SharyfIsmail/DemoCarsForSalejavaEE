package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.exceptions.ForbiddenActionException;
import com.example.demoCarsForSale.pojo.Ad;
import com.example.demoCarsForSale.pojo.Pic;
import com.example.demoCarsForSale.repository.AdRepository;
import com.example.demoCarsForSale.repository.PicRepository;
import com.example.demoCarsForSale.services.PicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("picService")
@RequiredArgsConstructor
public class PicServiceImpl implements PicService {
    private final PicRepository picRepository;
    private final AdRepository adRepository;

    @Transactional
    @Override
    public void delete(long id, long userId) {
        Pic picToDelete = picRepository.findPicWithAdByPicId(id).orElseThrow(
            () -> new EntityNotFoundException("pic is missing"));

        isValidAction(isAbleToDelete(userId, picToDelete.getAd().getUser().getUserId()),
            () -> new ForbiddenActionException("Permission denied"));

        Ad ad = adRepository.findAdWithPicByAdId(picToDelete.getAd().getAdId())
            .orElseThrow(() -> new EntityNotFoundException("Ad is missing"));

        ad.removePicFromAd(picToDelete);

        adRepository.save(ad);
    }
}
