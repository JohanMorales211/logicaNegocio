package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.City
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class CityRepositoryTest {

    @Autowired
    lateinit var cityRepository: CityRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        cityRepository.create(City(2L, "Pereira"))

        val city = entityManager.find(City::class.java,2L)
        Assertions.assertNotNull(city)
        Assertions.assertEquals("Pereira", city.name)
        Assertions.assertEquals(2L, city.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        entityManager.persist(City(2L, "Pereira"))

        //ejecutando...
        val city = entityManager.find(City::class.java, 2L)
        city.name = "Armenia"

        cityRepository.update(city)

        //assersiones
        val cityAssert = entityManager.find(City::class.java, 2L)
        Assertions.assertEquals("Armenia", cityAssert.name)
    }

    @Test
    fun findTest() {
        entityManager.persist(City(2L, "Pereira"))

        val city = cityRepository.find(2L)

        Assertions.assertNotNull(city)
        Assertions.assertEquals("Pereira", city?.name)
    }

    @Test
    fun testDelete() {
        entityManager.persist(City(2L, "Pereira"))
        cityRepository.delete(2L)

        val city = entityManager.find(City::class.java, 2L)
        Assertions.assertNull(city)
    }
}