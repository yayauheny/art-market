package by.yayauheny.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import by.yayauheny.entity.CategoryEntity;
import by.yayauheny.integration.IntegrationBaseTest;
import by.yayauheny.util.TestDataUtil;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CategoryEntityIT extends IntegrationBaseTest {

  @Test
  void save_validCategory_saved() {
    var category = TestDataUtil.getCategory("paintings");

    session.persist(category);
    session.flush();
    session.clear();
    var savedCategory = session.get(CategoryEntity.class, category.getId());

    assertThat(savedCategory).isNotNull();
  }

  @Test
  void findById_categoryNotExist_notFound() {
    var categoryId = UUID.fromString("11059f0e-669f-43e6-9087-21f9a114deb5");

    var foundCategory = session.get(CategoryEntity.class, categoryId);

    assertThat(foundCategory).isNull();
  }

  @Test
  void findById_multipleCategories_foundAll() {
    var firstCategory = TestDataUtil.getCategory("paintings");
    var secondCategory = TestDataUtil.getCategory("postcards");
    session.persist(firstCategory);
    session.persist(secondCategory);
    session.flush();
    session.clear();

    var firstSavedCategory = session.get(CategoryEntity.class, firstCategory.getId());
    var secondSavedCategory = session.get(CategoryEntity.class, secondCategory.getId());

    assertThat(List.of(firstSavedCategory, secondSavedCategory))
        .allMatch(Objects::nonNull);
  }

  @Test
  void remove_categoryExist_removed() {
    var category = TestDataUtil.getCategory("paintings");
    session.persist(category);
    session.flush();
    session.clear();
    var savedCategory = session.get(CategoryEntity.class, category.getId());

    session.remove(savedCategory);
    session.flush();
    session.clear();
    var foundCategory = session.get(CategoryEntity.class, savedCategory.getId());

    assertThat(foundCategory).isNull();
  }

  @Test
  void update_updatedCategoryDescription_updated() {
    var category = TestDataUtil.getCategory("paintings");
    var updatedDescription = "updated description";
    session.persist(category);
    session.flush();
    session.clear();
    var savedCategory = session.get(CategoryEntity.class, category.getId());

    savedCategory.setDescription(updatedDescription);
    session.merge(savedCategory);
    session.flush();
    session.clear();
    var updatedCategory = session.get(CategoryEntity.class, savedCategory.getId());

    assertThat(updatedCategory)
        .isNotNull()
        .isEqualTo(savedCategory);
  }
}