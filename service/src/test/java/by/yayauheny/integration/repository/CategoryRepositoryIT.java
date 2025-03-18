package by.yayauheny.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.CategoryEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.repository.CategoryRepository;
import by.yayauheny.util.IocIntegrationTest;
import by.yayauheny.util.TestDataUtil;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(IocIntegrationTest.class)
class CategoryRepositoryIT extends IntegrationBaseTest {

  private CategoryRepository categoryRepository;

  @BeforeEach
  void initDependencies() {
    categoryRepository = new CategoryRepository(session);
  }

  @Test
  void save_validCategory_saved() {
    var category = createAndSaveCategory();

    var savedCategory = categoryRepository.findById(category.getId()).get();
    assertThat(savedCategory.getId()).isEqualTo(category.getId());
  }

  @Test
  void findById_categoryNotExist_notFound() {
    var categoryId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundCategory = categoryRepository.findById(categoryId);

    assertThat(foundCategory).isEmpty();
  }

  @Test
  void findById_categoryExist_found() {
    var category = createAndSaveCategory();

    var savedCategory = categoryRepository.findById(category.getId()).get();

    assertThat(savedCategory.getId()).isEqualTo(category.getId());
  }

  @Test
  void delete_categoryExist_deleted() {
    var category = createAndSaveCategory();
    var savedCategory = categoryRepository.findById(category.getId()).get();

    categoryRepository.delete(savedCategory);

    session.clear();
    var foundCategory = categoryRepository.findById(savedCategory.getId());
    assertThat(foundCategory).isEmpty();
  }

  @Test
  void update_updatedCategoryDescription_updated() {
    var category = createAndSaveCategory();
    var savedCategory = categoryRepository.findById(category.getId()).get();
    var updatedDescription = "updated description";
    savedCategory.setDescription(updatedDescription);

    categoryRepository.update(savedCategory);

    session.flush();
    session.clear();
    var updatedCategory = categoryRepository.findById(savedCategory.getId()).get();
    assertThat(updatedCategory.getDescription()).isEqualTo(updatedDescription);
  }

  private CategoryEntity createAndSaveCategory() {
    var category = TestDataUtil.getCategory("paintings");
    var savedCategory = categoryRepository.save(category);
    session.flush();
    session.clear();

    return savedCategory;
  }
}