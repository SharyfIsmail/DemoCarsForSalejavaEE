package com.example.demoCarsForSale.dao.impl;

import com.example.demoCarsForSale.dao.UserPhoneDao;
import com.example.demoCarsForSale.dao.model.UserPhone;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository("userPhoneDao")
public class UserPhoneDaoImpl extends AbstractDao implements UserPhoneDao {

    @Override
    public List<UserPhone> savePhones(List<UserPhone> userPhones) {
        EntityManager entityManager = entityManager();
        userPhones.forEach(entityManager::persist);

        return userPhones;
    }

    @Override
    public Class<UserPhone> getClassType() {
        return UserPhone.class;
    }
}
