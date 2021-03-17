package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.controllers.dto.request.AdRequest;
import com.example.demoCarsForSale.controllers.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.controllers.dto.response.AdResponse;
import com.example.demoCarsForSale.dao.AdDao;
import com.example.demoCarsForSale.dao.UserDao;
import com.example.demoCarsForSale.dao.impl.AdDaoImpl;
import com.example.demoCarsForSale.dao.impl.UserDaoImpl;
import com.example.demoCarsForSale.dao.model.Ad;
import com.example.demoCarsForSale.dao.model.Pic;
import com.example.demoCarsForSale.dao.model.User;
import com.example.demoCarsForSale.dao.model.UserPhone;
import com.example.demoCarsForSale.exeptions.BadRequestException;
import com.example.demoCarsForSale.exeptions.InternalErrorException;
import com.example.demoCarsForSale.mapper.AdResponseRequestMapper;
import com.example.demoCarsForSale.mapper.PicRequestResponseMapper;
import com.example.demoCarsForSale.mapper.UserPhoneRequestResponseMapper;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.PicService;
import com.example.demoCarsForSale.services.UserPhoneService;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AdServiceHandler extends AbstractService implements AdService {
    private static final AdDao AD_DAO = new AdDaoImpl();
    private static final PicService PIC_SERVICE_HANDLER = new PicServiceHandler();
    private static final UserPhoneService USER_PHONE_SERVICE = new UserPhoneServiceHandler();
    private static final UserDao USER_DAO = new UserDaoImpl();

    @Override
    public AdDetailedResponse save(AdRequest adRequest, long userId) {
        AdDetailedResponse adDetailedResponse;

        try {
            Ad ad = AD_DAO.save(AdResponseRequestMapper.convertUserAdRequestToAd(adRequest, userId));
            adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);

            List<Pic> pics = PIC_SERVICE_HANDLER
                .save(PicRequestResponseMapper
                    .convertPicRequestToPic(adRequest.getPics(), ad.getAdId()));

            adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(pics));

            List<UserPhone> phones = USER_PHONE_SERVICE
                .save(UserPhoneRequestResponseMapper
                    .convertPhoneRequestToUserPhone(adRequest.getPhones(), ad.getUserId()));

            adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(phones));

            commit();
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
            adDetailedResponse = AdResponseRequestMapper.convertAdToAdDetailedResponse(ad);
            List<Pic> pics = PIC_SERVICE_HANDLER.get(adId);
            List<UserPhone> phones = USER_PHONE_SERVICE.get(ad.getUserId());
            adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.convertUserPhoneToPhoneResponse(phones));
            adDetailedResponse.setPics(PicRequestResponseMapper.convertPicToPicResponse(pics));
        } catch (SQLException e) {
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
            List<Ad> ads = AD_DAO.getRecords(page, total);
            adResponses = ads.stream().map(x -> {
                try {
                    User user = USER_DAO.get(x.getUserId());

                    return AdResponse.builder()
                        .adId(x.getAdId())
                        .year(x.getYear())
                        .condition(x.getCondition())
                        .brand(x.getBrand())
                        .model(x.getModel())
                        .userName(user.getName())
                        .createDate(x.getCreateDate())
                        .build();
                } catch (SQLException e) {
                    throw new InternalErrorException("Oops, something went wrong while fetching all ads", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            throw new InternalErrorException("Oops, something went wrong while fetching all ads", e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return adResponses;
    }
}
