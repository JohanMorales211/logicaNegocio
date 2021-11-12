package eam.edu.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import eam.edu.ingesoft.onlinestore.model.entities.City
import eam.edu.ingesoft.onlinestore.model.entities.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
//arrancar el servidor web
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createUserHappyPathTest() {
        //prerequisitos..
        entityManager.persist(City(1L, "Pereira"))

        val body = """
           {
            "id": "1",
            "address": "Centro",
            "name": "Johan",
            "lastName": "Morales",
            "city":{
                "id": 1,
                "name": "Pereira"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createUserNotFoundTest() {
        val city = City(1L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(User("1","Centro","Johan", "Morales", city))
        val body = """
           {
            "id": "1",
            "address": "Centro",
            "name": "Johan",
            "lastName": "Morales",
            "city":{
                "id": 1,
                "name": "Pereira"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This person already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editUserHappyPathTest() {
        //prerequisitos..
        val city = City(1L, "Pereira")
        entityManager.persist(city)
        entityManager.persist(User("1","Centro","Johan", "Morales", city))

        val body = """
           {
            "id": "1",
            "address": "La 25",
            "name": "Ñeñe",
            "lastName": "Ñuca",
            "city":{
                "id": 1,
                "name": "Armenia"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .put("/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }
}