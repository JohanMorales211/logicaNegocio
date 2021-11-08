package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.model.entities.City
import eam.edu.ingesoft.onlinestore.model.entities.Store
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class StoreServiceTest {

    @Autowired
    lateinit var storeService: StoreService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createStoreHappyPathTest(){
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        storeService.createStore(Store("5", "CarnesYY", "Carnitas la Y", city))

        val storeToAssert = entityManager.find(Store::class.java, "1")
        Assertions.assertNotNull(storeToAssert)
        Assertions.assertEquals("CarnesYY", storeToAssert.name)
    }
}