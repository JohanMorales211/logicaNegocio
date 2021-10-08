package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.Category
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class CategoryRepositoryTest {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        categoryRepository.create(Category(10L,"Gasimba"))

        val category = entityManager.find(Category::class.java,10L)
        Assertions.assertNotNull(category)
        Assertions.assertEquals("Gasimba", category.name)
        Assertions.assertEquals(10L, category.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(Category(10L,"Gasimba"))

        //ejecutando...
        val category = entityManager.find(Category::class.java, 10L)
        category.name = "Papitas"

        categoryRepository.update(category)

        //assersiones
        val categoryAssert = entityManager.find(Category::class.java, 10L)
        Assertions.assertEquals("Papitas", categoryAssert.name)
    }

    @Test
    fun findTest() {
        entityManager.persist(Category(10L,"Gasimba"))

        val category = categoryRepository.find(10L)

        Assertions.assertNotNull(category)
        Assertions.assertEquals("Gasimba", category?.name)
    }

    @Test
    fun testDelete() {
        entityManager.persist(Category(10L,"Gasimba"))
        categoryRepository.delete(10L)

        val category = entityManager.find(Category::class.java, 10L)
        Assertions.assertNull(category)
    }
}