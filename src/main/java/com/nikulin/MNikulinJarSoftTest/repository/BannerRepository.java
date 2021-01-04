package com.nikulin.MNikulinJarSoftTest.repository;

import com.nikulin.MNikulinJarSoftTest.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Integer> {
    Optional<Banner> findByName(String name);

    Optional<Banner> findByBannerIdAndDeleted(Integer id, Boolean deleted);

    List<Banner> findAllByDeleted(Boolean deleted);

    List<Banner> findAllByCategory_CategoryIdAndDeleted(Integer categoryId, Boolean deleted);

    Optional<Banner> findFirstByBannerIdNotInAndCategory_ReqNameAndDeletedOrderByPriceDesc(
            Collection<Integer> bannerIdStopList, String reqName, Boolean deleted);
}
