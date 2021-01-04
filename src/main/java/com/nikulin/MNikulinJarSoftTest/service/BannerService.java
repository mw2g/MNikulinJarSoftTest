package com.nikulin.MNikulinJarSoftTest.service;

import com.nikulin.MNikulinJarSoftTest.domain.Banner;
import com.nikulin.MNikulinJarSoftTest.domain.Category;
import com.nikulin.MNikulinJarSoftTest.repository.BannerRepository;
import com.nikulin.MNikulinJarSoftTest.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class BannerService {
    private final BannerRepository bannerRepository;
    private final CategoryRepository categoryRepository;

    public List<Banner> getAll() {
        List<Banner> allByDeleted = bannerRepository.findAllByDeleted(false);
        return allByDeleted;
    }

    public Banner getById(Integer typeId) {
        Banner banner = bannerRepository.findByBannerIdAndDeleted(typeId, false)
                .orElseThrow(() -> new RuntimeException("Can`t find banner by bannerId, maybe it deleted"));
        return banner;
    }

    /**
     * The method to create banner in database.
     * @param banner from client to create
     * @return banner from database after create or same banner from client with
     * nullified field name if it has duplicate in database by this fields.
     */
    public Banner create(Banner banner) {
        if (checkForDuplicate(banner)) {
            return banner;
        }
        Banner bannerToSave = new Banner();
        Category category = categoryRepository.findByName(banner.getCategory().getName()).get();
        bannerToSave.setCategory(category);
        bannerToSave.setName(banner.getName());
        bannerToSave.setPrice(banner.getPrice());
        bannerToSave.setContent(banner.getContent());
        bannerToSave.setDeleted(false);
        return bannerRepository.save(bannerToSave);
    }

    /**
     * The method checks the banner for a duplicate in the database by the field name.
     * If there is a duplicate, it returns true and nullifies duplicated fields.
     * @param banner received from the client to create or update data.
     * @return true if duplicate exist, false otherwise.
     */
    public boolean checkForDuplicate(Banner banner) {
        boolean duplicate = false;
        Optional<Banner> byName = bannerRepository.findByName(banner.getName());
        if (byName.isPresent() && byName.get().getBannerId() != banner.getBannerId()) {
            banner.setName(null);
            duplicate = true;
        }
        return duplicate;
    }

    /**
     * The method to update banner in database by bannerId field.
     * @param banner from client to update
     * @return banner from database after update or same category from client with
     * nullified field name if it has duplicate in database by this fields.
     */
    public Banner update(Banner banner) {
        if (checkForDuplicate(banner)) {
            return banner;
        }
        Banner bannerToSave = bannerRepository.findByBannerIdAndDeleted(banner.getBannerId(), false)
                .orElseThrow(() -> new RuntimeException("Can`t find banner by bannerId to update"));
        Category category = categoryRepository.findByName(banner.getCategory().getName()).get();
        bannerToSave.setCategory(category);
        bannerToSave.setName(banner.getName());
        bannerToSave.setPrice(banner.getPrice());
        bannerToSave.setContent(banner.getContent());
        return bannerRepository.save(bannerToSave);
    }

    /**
     * The method sets field deleted to true.
     * @param bannerId received from the client to mark banner as deleted.
     */
    public void delete(Integer bannerId) {
        Banner bannerToDelete = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new RuntimeException("Can`t find banner by bannerId to delete"));
        bannerToDelete.setDeleted(true);
        bannerRepository.save(bannerToDelete);
    }
}
