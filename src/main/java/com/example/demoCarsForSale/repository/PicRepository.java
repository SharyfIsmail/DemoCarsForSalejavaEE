package com.example.demoCarsForSale.repository;

import com.example.demoCarsForSale.pojo.Pic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PicRepository extends JpaRepository<Pic, Long> {

    @EntityGraph(attributePaths = "ad")
    Optional<Pic> findPicWithAdByPicId(@Param("picId") long picId);
}
