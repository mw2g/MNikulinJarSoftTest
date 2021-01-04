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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;

    public List<Category> getAll() {
        return categoryRepository.findAllByDeleted(false);
    }

    public Category getById(Integer typeId) {
        Category category = categoryRepository.findByCategoryIdAndDeleted(typeId, false)
                .orElseThrow(() -> new RuntimeException("Can`t find category by categoryId, maybe it deleted"));
        return category;
    }

    /**
     * The method to create category in database.
     * @param category from client to create
     * @return category from database after create or same category from client with
     * nullified field name and/or field reqName if it has duplicate in database by this fields.
     */
    public Category create(Category category) {
        if (checkForDuplicate(category)) {
            return category;
        }
        Category categoryToSave = new Category();
        categoryToSave.setName(category.getName());
        categoryToSave.setReqName(category.getReqName());
        categoryToSave.setDeleted(false);
        return categoryRepository.save(categoryToSave);
    }

    /**
     * The method to update category in database by categoryId field.
     * @param category from client to update
     * @return category from database after update or same category from client with
     * nullified field name and/or field reqName if it has duplicate in database by this fields.
     */
    public Category update(Category category) {
        if (checkForDuplicate(category)) {
            return category;
        }
        Category categoryToUpdate = categoryRepository.findByCategoryIdAndDeleted(category.getCategoryId(), false)
                .orElseThrow(() -> new RuntimeException("Can`t find category by categoryId to update"));
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setReqName(category.getReqName());
        return categoryRepository.save(categoryToUpdate);
    }

    /**
     * The method sets field deleted to true if category do not use in undeleted banners.
     * @param categoryId received from the client to mark category as deleted.
     * @return empty string if delete successful or string with ids and names of banners using this category.
     */
    public String delete(Integer categoryId) {
        String response = checkForUseInBanners(categoryId);
        if (!response.isEmpty()) {
            return response;
        }
        Category categoryToDelete = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Can`t find category by categoryId to delete"));
        categoryToDelete.setDeleted(true);
        categoryRepository.save(categoryToDelete);
        return "";
    }

    /**
     * The method checks the category for a duplicate in the database by the fields name and request name.
     * If there is a duplicate, it returns true and nullifies the duplicated fields.
     * @param category received from the client to create or update data.
     * @return true if duplicate exist, false otherwise.
     */
    public boolean checkForDuplicate(Category category) {
        boolean duplicate = false;
        Optional<Category> byName = categoryRepository.findByName(category.getName());
        if (byName.isPresent() && byName.get().getCategoryId() != category.getCategoryId()) {
            category.setName(null);
            duplicate = true;
        }
        Optional<Category> byReqName = categoryRepository.findByReqName(category.getReqName());
        if (byReqName.isPresent() && byReqName.get().getCategoryId() != category.getCategoryId()) {
            category.setReqName(null);
            duplicate = true;
        }
        return duplicate;
    }

    /**
     * The method checks if the category is used in banners that are not marked as deleted.
     * @param categoryId of the checked category
     * @return String with ids and names of banners using this category.
     */
    public String checkForUseInBanners(Integer categoryId) {
        String result = "";
        List<Banner> banners = bannerRepository.findAllByCategory_CategoryIdAndDeleted(categoryId, false);
        for (Banner banner : banners) {
            result += banner.getBannerId() + " " + banner.getName() + ". ";
        }
        return result;
    }
}
