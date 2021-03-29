package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.controllers.dto.response.AdResponse;
import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.PicDao;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.impl.AdDaoImpl;
import com.example.demoCarsForSale.dao.impl.PicDaoImpl;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.impl.UserPhoneDaoImpl;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pagination;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.mapper.AdResponseRequestMapper;
import com.example.demoCarsForSale.mapper.PaginationMapper;
import com.example.demoCarsForSale.mapper.PicRequestResponseMapper;
import com.example.demoCarsForSale.mapper.UserPhoneRequestResponseMapper;
import com.example.demoCarsForSale.services.AdHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdService extends AbstractService implements AdHandler {
    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final AdDao AD_DAO = new AdDaoImpl();
    private static final PicDao PIC_DAO = new PicDaoImpl();
    private static final UserPhoneDao USER_PHONE = new UserPhoneDaoImpl();

    @Override
    public AdDetailedResponse createAd(AdRequest model, long userId) {
        List<UserPhone> phones = UserPhoneRequestResponseMapper.convertPhoneRequestToUserPhone(model.getPhones());
        List<Pic> pics = PicRequestResponseMapper.convertPicRequestToPic(model.getPics());
        Ad ad = AdResponseRequestMapper.convertUserAdRequestToAd(model);

        try {
            startTransaction();

            User user = USER_DAO.findById(userId);
            ad.setUser(user);
            Ad createdAd = AD_DAO.save(ad);

            pics.forEach(x -> x.setAd(createdAd));
            PIC_DAO.savePics(pics);

            phones.forEach(x -> x.setUser(user));
            USER_PHONE.savePhones(phones);

            closeTransaction();
        } catch (Exception e) {
            rollback();
            throw new BadRequestException("Make sure to type all required fields", e, HttpServletResponse.SC_BAD_REQUEST);
        }

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(pics));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(phones));

        return adDetailedResponse;
    }

    @Override
    public AdDetailedResponse getDetailedInfoAboutAd(long id) {
        startTransaction();

        User user;
        Ad ad;
        if (AD_DAO.existsById(id)) {
            ad = AD_DAO.getDetailedInfoAboutAd(id);
            user = USER_DAO.findUserWithPhones(ad.getUser().getId());

            closeTransaction();
        } else {
            rollback();
            throw new EntityNotFoundException("Ad not found", HttpServletResponse.SC_NOT_FOUND);
        }

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(ad.getPics()));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(user.getUserPhones()));

        return adDetailedResponse;
    }

    @Override
    public void deleteAd(long adId, long userId) {
        startTransaction();

        Ad adToDelete = AD_DAO.existsById(adId) ?
            AD_DAO.getDetailedInfoAboutAd(adId) :
            null;

        if (adToDelete != null && isAbleToDelete(adToDelete.getUser().getId(), userId)) {
            AD_DAO.delete(adId);
            closeTransaction();
        } else {
            rollback();
            throw new BadRequestException("Permission denied", HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public List<AdResponse> getRecords(int start, int total) {
        startTransaction();
        List<Pagination> paginations = AD_DAO.getRecords(start, total);
        closeTransaction();

        return PaginationMapper.convertPaginationToAdResponse(paginations);
    }
}
