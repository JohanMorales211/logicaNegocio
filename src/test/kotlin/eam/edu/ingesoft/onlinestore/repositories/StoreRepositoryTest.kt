package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.City
import eam.edu.ingesoft.onlinestore.model.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class StoreRepositoryTest {
    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        storeRepository.create(Store("5", "CarnesYY", "Carnitas la Y", city))

        val store = entityManager.find(Store::class.java, "5")
        Assertions.assertNotNull(store)
        Assertions.assertEquals("Carnitas la Y", store.name)
        Assertions.assertEquals("CarnesYY", store.address)
        Assertions.assertEquals("5", store.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(Store("5", "CarnesYY", "Carnitas la Y", city))

        //ejecutando...
        val store = entityManager.find(Store::class.java, "5")
        store.name = "SuperMercadoY"
        store.address = "Cra 26"

        storeRepository.update(store)

        //assersiones
        val storeAssert = entityManager.find(Store::class.java, "5")
        Assertions.assertEquals("SuperMercadoY", storeAssert.name)
        Assertions.assertEquals("Cra 26", storeAssert.address)
    }

    @Test
    fun findTest() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(Store("5", "CarnesYY", "Carnitas la Y", city))

        val user = storeRepository.find("5")

        Assertions.assertNotNull(user)
        Assertions.assertEquals("Carnitas la Y", user?.name)
        Assertions.assertEquals("CarnesYY", user?.address)
    }

    @Test
    fun testDelete() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(Store("5", "CarnesYY", "Carnitas la Y", city))
        storeRepository.delete("5")

        val category = entityManager.find(Store::class.java, "5")
        Assertions.assertNull(category)
    }
}