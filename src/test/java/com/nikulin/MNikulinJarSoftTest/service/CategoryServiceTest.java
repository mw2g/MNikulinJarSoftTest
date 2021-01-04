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
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BannerRepository bannerRepository;

    private Category createdCategory;

    private Category newCategory = new Category(null, "nameTestCategoryForSpringBootTest",
            "reqNameTestCategoryForSpringBootTest", false);

    @BeforeEach
    void setUp() {
        Optional<Category> category = categoryRepository.findByName("nameTestCategoryForSpringBootTest");
        category.ifPresent(value -> categoryRepository.delete(value));
        createdCategory = categoryService.create(newCategory);
    }

    @AfterEach
    void tearDown() {
        categoryRepository.delete(createdCategory);
    }

    @Test
    void whenCreateThenGetCreatedCategory() {
        assertNotNull(createdCategory);
        assertNotNull(createdCategory.getCategoryId());
        assertEquals(createdCategory.getName(), "nameTestCategoryForSpringBootTest");
        assertEquals(createdCategory.getReqName(), "reqNameTestCategoryForSpringBootTest");
        assertFalse(createdCategory.getDeleted());
    }

    @Test
    void whenGetByIdOfCatThenGetSameCategory() {
        Category categoryById = categoryService.getById(createdCategory.getCategoryId());
        assertEquals(createdCategory.getCategoryId(), categoryById.getCategoryId());
    }

    @Test
    void whenGetAllThenListNotEmpty() {
        List<Category> categories = categoryService.getAll();
        assertFalse(categories.isEmpty());
    }

    @Test
    void whenDeleteCategoryThenFieldDeletedIsTrue() {
        categoryService.delete(createdCategory.getCategoryId());
        createdCategory = categoryRepository.findByName("nameTestCategoryForSpringBootTest").get();
        assertTrue(createdCategory.getDeleted());
    }

    @Test
    void whenCheckForDuplicateGetDuplicateCatTheyReturnTrue() {
        assertTrue(categoryService.checkForDuplicate(newCategory));
    }

    @Test
    void whenCheckForDuplicateGetNewCatTheyReturnFalse() {
        newCategory.setName("newNameTestCategoryForSpringBootTest");
        newCategory.setReqName("newReqNameTestCategoryForSpringBootTest");
        assertFalse(categoryService.checkForDuplicate(newCategory));
    }

    @Test
    void whenCheckForUseInBannersGetNewCatTheyReturnEmptyString() {
        assertEquals(categoryService.checkForUseInBanners(createdCategory.getCategoryId()), "");
    }

    @Test
    void whenCheckForUseInBannersGetUsingCatTheyReturnStringOfBannersUsingIt() {
        Banner newBanner = new Banner(null, "nameTestBannerForSpringBootTest", 99.0,
                "testContent", false, createdCategory);
        Optional<Banner> banner = bannerRepository.findByName("TestCategoryForSpringBootTest");
        if (banner.isPresent()) {
            bannerRepository.delete(banner.get());
        }
        Banner createdBanner = bannerRepository.save(newBanner);

        assertEquals(categoryService.checkForUseInBanners(createdCategory.getCategoryId()),
                createdBanner.getBannerId() + " " + createdBanner.getName() + ". ");
        bannerRepository.delete(createdBanner);
    }

}
