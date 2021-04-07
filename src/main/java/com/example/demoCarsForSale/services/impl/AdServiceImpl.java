package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.mapper.AdResponseRequestMapper;
import com.example.demoCarsForSale.services.mapper.PaginationMapper;
import com.example.demoCarsForSale.services.mapper.PicRequestResponseMapper;
import com.example.demoCarsForSale.services.mapper.UserPhoneRequestResponseMapper;
import com.example.demoCarsForSale.web.dto.projection.AdShortInfo;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.AdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adService")
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final UserDao userDao;
    private final AdDao adDao;
    private final PicDao picDao;
    private final UserPhoneDao userPhoneDao;

    @Override
    public AdDetailedResponse createAd(AdRequest model, long userId) {
        List<UserPhone> phones = UserPhoneRequestResponseMapper.toUserPhones(model.getPhones());
        List<Pic> pics = PicRequestResponseMapper.toPics(model.getPics());
        Ad ad = AdResponseRequestMapper.toAd(model);

        User user = userDao.findById(userId);
        ad.setUser(user);
        Ad createdAd = adDao.save(ad);

        pics.forEach(x -> x.setAd(createdAd));
        picDao.savePics(pics);

        phones.forEach(x -> x.setUser(user));
        userPhoneDao.savePhones(phones);

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.toDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.toResponses(pics));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.toResponses(phones));

        return adDetailedResponse;
    }

    @Override
    public AdDetailedResponse getDetailedInfoAboutAd(long id) {
        User user;
        Ad ad;
        if (adDao.existsById(id)) {
            ad = adDao.getDetailedInfoAboutAd(id);
            user = userDao.findUserWithPhones(ad.getUser().getUserId());
        } else {
            throw new EntityNotFoundException("Ad not found");
        }

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.toDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.toResponses(ad.getPics()));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.toResponses(user.getUserPhones()));

        return adDetailedResponse;
    }

    @Override
    public void deleteAd(long adId, long userId) {
        Ad adToDelete = adDao.existsById(adId) ?
            adDao.getDetailedInfoAboutAd(adId) :
            null;

        if (adToDelete != null && isAbleToDelete(adToDelete.getUser().getUserId(), userId)) {
            adDao.delete(adId);
        } else {
            throw new BadRequestException("Permission denied");
        }
    }

    @Override
    public List<AdResponse> getRecords(int start, int total) {
        List<AdShortInfo> adShortInfos = adDao.getRecords(start, total);

        return PaginationMapper.toResponses(adShortInfos);
    }
}
