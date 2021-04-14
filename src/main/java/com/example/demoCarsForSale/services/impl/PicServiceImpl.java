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

import java.time.LocalDateTime;

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
        Ad ad = picToDelete.getAd();

        isValidAction(isAbleToDelete(userId, ad.getUser().getUserId()),
            () -> new ForbiddenActionException("Permission denied"));

        picRepository.delete(picToDelete);
        ad.setEditDate(LocalDateTime.now());
        adRepository.save(ad);
    }
}
