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
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.mapper.AdResponseRequestMapper;
import com.example.demoCarsForSale.mapper.PaginationMapper;
import com.example.demoCarsForSale.mapper.PicRequestResponseMapper;
import com.example.demoCarsForSale.mapper.UserPhoneRequestResponseMapper;
import com.example.demoCarsForSale.services.AdService;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class AdServiceHandler extends AbstractService implements AdService {
    private static final AdDao AD_DAO = new AdDaoImpl();
    private static final PicDao PIC_DAO = new PicDaoImpl();
    private static final UserPhoneDao USER_PHONE_DAO = new UserPhoneDaoImpl();
    private static final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public AdDetailedResponse save(AdRequest adRequest, long userId) {
        AdDetailedResponse adDetailedResponse;

        try {
            Ad ad = AD_DAO.save(AdResponseRequestMapper.convertUserAdRequestToAd(adRequest, userId));
            List<Pic> pics = PIC_DAO.save(PicRequestResponseMapper
                .convertPicRequestToPic(adRequest.getPics(), ad.getUserId()));
            List<UserPhone> phones = USER_PHONE_DAO.save(UserPhoneRequestResponseMapper
                .convertPhoneRequestToUserPhone(adRequest.getPhones(), ad.getUserId()));

            commit();

            adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);
            adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(pics));
            adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(phones));
        } catch (SQLException e) {
            rollback();
            throw new BadRequestException("Make sure to type all required fields", e, HttpServletResponse.SC_BAD_REQUEST);
        }
        return adDetailedResponse;
    }

    @Override
    public AdDetailedResponse get(long adId) {
        AdDetailedResponse adDetailedResponse;

        try {
            Ad ad = AD_DAO.get(adId);
            List<Pic> pics = PIC_DAO.get(adId);
            List<UserPhone> phones = USER_PHONE_DAO.get(ad.getUserId());

            commit();

            adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);
            adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(phones));
            adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(pics));
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong while fetching ad", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return adDetailedResponse;
    }

    @Override
    public void delete(Ad ad) {
        try {
            AD_DAO.delete(ad);
            commit();
        } catch (SQLException e) {
            rollback();
            throw new InternalErrorException("Oops, something went wrong while deleting ad", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AdResponse> getRecords(int page, int total) {
        List<AdResponse> adResponses;

        try {
            List<Pagination> paginations = AD_DAO.getRecords(page, total);
            commit();
            adResponses = PaginationMapper.convertPaginationToAdResponse(paginations);
        } catch (SQLException e) {
            throw new InternalErrorException("Oops, something went wrong while fetching all ads", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return adResponses;
    }
}