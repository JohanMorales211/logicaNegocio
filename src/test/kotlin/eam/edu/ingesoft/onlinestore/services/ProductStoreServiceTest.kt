package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ProductStoreServiceTest {

    @Autowired
    lateinit var productStoreService: ProductStoreService

    @Autowired
    lateinit var entityManager: EntityManager

    fun createProductStoreHappyPathTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        productStoreService.createProductStore(ProductStore(2L, 500.000,50.0,product,store))

        val productStoreToAssert = entityManager.find(ProductStore::class.java, 2L)
        Assertions.assertNotNull(productStoreToAssert)
        Assertions.assertEquals(2L, productStoreToAssert.id)
    }

    @Test
    fun createProductStoreRepeatedIdTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        entityManager.persist(ProductStore(2L, 500.000,50.0,product,store))

        try {
            productStoreService.createProductStore(ProductStore(2L, 700.000,100.0,product,store))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This product already exists on the store", e.message)
        }
    }

    @Test
    fun decreaseProductstoreNotStock(){
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        entityManager.persist(ProductStore(2L, 500.000,50.0,product,store))

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { productStoreService.decreaseStockProductStore(ProductStore(2L, 200.000,30.0,product,store),50.0) }
        )

        Assertions.assertEquals("There is not stock", exception.message)
    }
}