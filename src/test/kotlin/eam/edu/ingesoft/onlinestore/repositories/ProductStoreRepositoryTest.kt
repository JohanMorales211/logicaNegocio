package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ProductStoreRepositoryTest {

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        productStoreRepository.create(ProductStore(2L, 500.000,50.0,product,store))

        val productStore = entityManager.find(ProductStore::class.java,2L)
        Assertions.assertNotNull(productStore)
        Assertions.assertEquals(500.000, productStore.price)
        Assertions.assertEquals(2L, productStore.id)
        Assertions.assertEquals(50.0, productStore.stock)
    }

    @Test
    fun testUpdate() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        productStoreRepository.create(ProductStore(2L, 500.000,50.0,product,store))

        //ejecutando...
        val productStore = entityManager.find(ProductStore::class.java, 2L)
        productStore.price = 800.000
        productStore.stock = 1000.0

        productStoreRepository.update(productStore)

        //assersiones
        val productStoreAssert = entityManager.find(ProductStore::class.java, 2L)
        Assertions.assertEquals(800.000, productStoreAssert.price)
        Assertions.assertEquals(1000.0, productStoreAssert.stock)
    }

    @Test
    fun findTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        productStoreRepository.create(ProductStore(2L, 500.000,50.0,product,store))

        val productStore = productStoreRepository.find(2L)

        Assertions.assertNotNull(productStore)
        Assertions.assertEquals(500.000, productStore?.price)
        Assertions.assertEquals(50.0, productStore?.stock)
    }

    @Test
    fun testDelete() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        val product = Product(5L,"YeyeCoffe","CocaCola",category)
        entityManager.persist(product)
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        val store = Store("5", "CarnesYY", "Carnitas la Y", city)
        productStoreRepository.create(ProductStore(2L, 500.000,50.0,product,store))
        productStoreRepository.delete(2L)

        val productStore = entityManager.find(ProductStore::class.java, 2L)
        Assertions.assertNull(productStore)
    }
}