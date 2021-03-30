package com.example.demoCarsForSale.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private long id;

    @Column(name = "USER_NAME", nullable = false, length = 20)
    private String name;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 30)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 15)
    private String password;

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Ad> ads;

    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<UserPhone> userPhones;

    public void addAdToUser(Ad ad) {
        ads.add(ad);
        ad.setUser(this);
    }

    public void addPhoneToUser(UserPhone userPhone) {
        userPhones.add(userPhone);
        userPhone.setUser(this);
    }
}
