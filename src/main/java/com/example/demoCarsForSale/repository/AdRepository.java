package com.example.demoCarsForSale.repository;

import com.example.demoCarsForSale.pojo.Ad;
import com.example.demoCarsForSale.web.dto.projection.AdShortInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {

    @EntityGraph(attributePaths = {"user", "pics"})
    Slice<AdShortInfo> findAllBy(Pageable page);

    @EntityGraph(attributePaths = "pics")
    Optional<Ad> findAdWithPicByAdId(@Param("adId") long adId);
}
