package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.City
import eam.edu.ingesoft.onlinestore.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun contextLoads() {
    }

    @Test
    fun testCreate() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userRepository.create(User("1", "CarnesYY", "Leucebio","Tafur", city))

        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNotNull(user)
        Assertions.assertEquals("Leucebio", user.name)
        Assertions.assertEquals("Tafur", user.lastName)
        Assertions.assertEquals("CarnesYY", user.address)
        Assertions.assertEquals("1", user.id)
    }

    @Test
    fun testUpdate() {
        //prerequisito
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userRepository.create(User("1", "CarnesYY", "Leucebio","Tafur", city))

        //ejecutando...
        val user = entityManager.find(User::class.java, "1")
        user.name = "Arcesio"
        user.lastName = "Bernal"
        user.address = "Cra 25"

        userRepository.update(user)

        //assersiones
        val userAssert = entityManager.find(User::class.java, "1")
        Assertions.assertEquals("Arcesio", userAssert.name)
        Assertions.assertEquals("Bernal", userAssert.lastName)
        Assertions.assertEquals("Cra 25", userAssert.address)
    }

    @Test
    fun findTest() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userRepository.create(User("1", "CarnesYY", "Leucebio","Tafur", city))

        val user = userRepository.find("1")

        Assertions.assertNotNull(user)
        Assertions.assertEquals("Leucebio", user?.name)
        Assertions.assertEquals("Tafur", user?.lastName)
        Assertions.assertEquals("CarnesYY", user?.address)
    }

    @Test
    fun testDelete() {
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userRepository.create(User("1", "CarnesYY", "Leucebio","Tafur", city))
        userRepository.delete("1")

        val user = entityManager.find(User::class.java, "1")
        Assertions.assertNull(user)
    }
}