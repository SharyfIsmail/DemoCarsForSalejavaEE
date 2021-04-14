package com.example.demoCarsForSale.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ADS")
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AD_ID", nullable = false)
    private Long adId;

    @Column(name = "YEAR", nullable = false)
    private int year;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "ENGINE_CAPACITY")
    private int engineCapacity;

    @Column(name = "CONDITION", nullable = false)
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "MILEAGE")
    private int mileage;

    @Column(name = "POWER")
    private int power;

    @Column(name = "CREATING_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "EDIT_DATE", nullable = false)
    private LocalDateTime editDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ad",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<Pic> pics;

    public void addToPic(Pic pic) {
        pics.add(pic);
        pic.setAd(this);
    }

    public void removePicFromAd(Pic pic) {
        pics.remove(pic);
        pic.setAd(null);
    }
}
