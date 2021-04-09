package com.example.demoCarsForSale.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PICS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PIC_ID", nullable = false)
    private Long picId;

    @Column(name = "PIC")
    private String carPic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Ad ad;
}
