package com.nikulin.MNikulinJarSoftTest.service;

import com.nikulin.MNikulinJarSoftTest.domain.Banner;
import com.nikulin.MNikulinJarSoftTest.domain.Category;
import com.nikulin.MNikulinJarSoftTest.repository.BannerRepository;
import com.nikulin.MNikulinJarSoftTest.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BannerServiceTest {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BannerRepository bannerRepository;

    private Banner createdBanner;
    private Category createdCategory;

    private Category newCategory = new Category(null, "nameTestCategoryForSpringBootTest",
            "reqNameTestCategoryForSpringBootTest", false);

    private Banner newBanner;

    @BeforeEach
    void setUp() {
        Optional<Banner> banner = bannerRepository.findByName("nameTestBannerForSpringBootTest");
        banner.ifPresent(value -> bannerRepository.delete(value));

        Optional<Category> category = categoryRepository.findByName("nameTestCategoryForSpringBootTest");
        category.ifPresent(value -> categoryRepository.delete(value));
        createdCategory = categoryRepository.save(newCategory);

        newBanner = new Banner(null, "nameTestBannerForSpringBootTest", 99.0,
                "testContent", false, createdCategory);

        createdBanner = bannerService.create(newBanner);
    }

    @AfterEach
    void tearDown() {
        bannerRepository.delete(createdBanner);
        categoryRepository.delete(createdCategory);
    }

    @Test
    void whenCreateThenGetCreatedBanner() {
        assertNotNull(createdBanner);
        assertNotNull(createdBanner.getBannerId());
        assertEquals(createdBanner.getName(), "nameTestBannerForSpringBootTest");
        assertEquals(createdBanner.getPrice(), 99.0);
        assertEquals(createdBanner.getContent(), "testContent");
        assertEquals(createdBanner.getCategory().getCategoryId(), createdCategory.getCategoryId());
        assertFalse(createdBanner.getDeleted());
    }

    @Test
    void whenGetByIdOfBannerThenGetSameBanner() {
        Banner bannerById = bannerService.getById(createdBanner.getBannerId());
        assertEquals(createdBanner.getBannerId(), bannerById.getBannerId());
    }

    @Test
    void whenGetAllThenListNotEmpty() {
        List<Banner> banners = bannerService.getAll();
        assertFalse(banners.isEmpty());
    }

    @Test
    void whenDeleteBannerThenFieldDeletedIsTrue() {
        bannerService.delete(createdBanner.getBannerId());
        createdBanner = bannerRepository.findByName("nameTestBannerForSpringBootTest").get();
        assertTrue(createdBanner.getDeleted());
    }

    @Test
    void whenCheckForDuplicateGetDuplicateBannerTheyReturnTrue() {
        assertTrue(bannerService.checkForDuplicate(newBanner));
    }

    @Test
    void whenCheckForDuplicateGetNewBannerTheyReturnFalse() {
        newBanner.setName("newNameTestBannerForSpringBootTest");
        assertFalse(bannerService.checkForDuplicate(newBanner));
    }

}
