package com.example.demoCarsForSale.services.impl;

import com.example.demoCarsForSale.exceptions.BadRequestException;
import com.example.demoCarsForSale.exceptions.EntityNotFoundException;
import com.example.demoCarsForSale.pojo.Ad;
import com.example.demoCarsForSale.pojo.Pic;
import com.example.demoCarsForSale.pojo.User;
import com.example.demoCarsForSale.pojo.UserPhone;
import com.example.demoCarsForSale.repository.AdRepository;
import com.example.demoCarsForSale.repository.PicRepository;
import com.example.demoCarsForSale.repository.UserPhoneRepository;
import com.example.demoCarsForSale.repository.UserRepository;
import com.example.demoCarsForSale.services.AdService;
import com.example.demoCarsForSale.services.mapper.AdResponseRequestMapper;
import com.example.demoCarsForSale.services.mapper.PaginationMapper;
import com.example.demoCarsForSale.services.mapper.PicRequestResponseMapper;
import com.example.demoCarsForSale.services.mapper.UserPhoneRequestResponseMapper;
import com.example.demoCarsForSale.web.dto.request.AdRequest;
import com.example.demoCarsForSale.web.dto.response.AdDetailedResponse;
import com.example.demoCarsForSale.web.dto.response.AdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Service("adService")
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final PicRepository picRepository;
    private final UserPhoneRepository userPhoneRepository;

    @Transactional
    @Override
    public AdDetailedResponse createAd(AdRequest model, long userId) {
        List<UserPhone> phones = UserPhoneRequestResponseMapper.toUserPhones(model.getPhones());
        List<Pic> pics = PicRequestResponseMapper.toPics(model.getPics());
        Ad ad = AdResponseRequestMapper.toAd(model);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Oops something went wrong, user was grabbed by aliens"));

        ad.setUser(user);
        Ad createdAd = adRepository.save(ad);

        pics.forEach(pic -> pic.setAd(createdAd));
        picRepository.saveAll(pics);

        phones.forEach(phone -> phone.setUser(user));
        userPhoneRepository.saveAll(phones);

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.toDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.toResponses(pics));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.toResponses(phones));

        return adDetailedResponse;
    }

    @Transactional
    @Override
    public AdDetailedResponse getDetailedInfoAboutAd(long id) {
        Ad ad = adRepository.findAdWithPicByAdId(id)
            .orElseThrow(() -> new EntityNotFoundException("Ad not found"));

        User user = userRepository.findUserWithUserPhonesByUserId(ad.getUser().getUserId())
            .orElseThrow(() -> new EntityNotFoundException("Oops something went wrong, user was grabbed by aliens"));

        AdDetailedResponse adDetailedResponse = AdResponseRequestMapper.toDetailedResponse(ad);
        adDetailedResponse.setPics(PicRequestResponseMapper.toResponses(ad.getPics()));
        adDetailedResponse.setPhones(UserPhoneRequestResponseMapper.toResponses(user.getUserPhones()));

        return adDetailedResponse;
    }

    @Transactional
    @Override
    public void deleteAd(long adId, long userId) {
        Ad adToDelete = adRepository.findById(adId)
            .orElseThrow(() -> new EntityNotFoundException("no ad to delete"));

        isValidAction(isAbleToDelete(adToDelete.getUser().getUserId(), userId),
            () -> new BadRequestException("Permission denied"));

        adRepository.deleteById(adId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<AdResponse> getRecords(Pageable pageable) {
        return PaginationMapper.toResponses(adRepository.findAllBy(pageable).getContent());
    }
}
