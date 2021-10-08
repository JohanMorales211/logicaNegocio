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
class UserServiceTest {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var userService: UserService

    @Test
    fun createUserHappyPathTest(){
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userService.createUser(User("1", "CarnesYY", "Leucebio","Tafur", city))

        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertNotNull(userToAssert)
        Assertions.assertEquals("Leucebio", userToAssert.name)
        Assertions.assertEquals("Tafur", userToAssert.lastName)
    }

    @Test
    fun createUserAlreadyExistsTest(){
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        userService.createUser(User("1", "CarnesYY", "Leucebio","Tafur", city))

        try {
            userService.createUser(User("1", "CarnesYY", "Leucebio","Tafur", city))
            Assertions.fail()
        } catch (e: BusinessException) {
            Assertions.assertEquals("This person already exists", e.message)
        }
    }

    @Test
    fun editUserHappyPathTest(){
        val city = City(2L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(User("1", "CarnesYY", "Leucebio","Tafur", city))

        val user = entityManager.find(User::class.java, "1")
        user.name = "Fabian"
        user.lastName = "Morales"

        userService.editUser(user)

        val userToAssert = entityManager.find(User::class.java, "1")
        Assertions.assertEquals("Fabian", userToAssert.name)
        Assertions.assertEquals("Morales", userToAssert.lastName)
    }

    @Test
    fun editStoreNotExistsTest(){
        val city = City(2L, "Pereira")
        entityManager.persist(city)

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { userService.editUser(User("1", "CarnesYY", "Leucebio","Tafur", city))}
        )

        Assertions.assertEquals("This user does not exists", exception.message)
    }


}