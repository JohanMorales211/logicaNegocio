package eam.edu.ingesoft.onlinestore.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import eam.edu.ingesoft.onlinestore.model.entities.Category
import eam.edu.ingesoft.onlinestore.model.entities.Product
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
class ProductControllerTest {
    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var mocMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun createProductHappyPathTest() {
        //prerequisitos..
        entityManager.persist(Category(1L, "Serie"))

        val body = """
           {
            "id": 1,
            "name": "Fanta",
            "branch": "Gaseosa",
            "category":{
                "id": 1,
                "name": "refresco"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }

    @Test
    fun createProductNotFoundTest() {
        val categrory = Category(1L, "Serie")
        entityManager.persist(categrory)
        entityManager.persist(Product(1L,"Fanta","Gaseosa",categrory))
        val body = """
           {
            "id": 1,
            "name": "Fanta",
            "branch": "Gaseosa",
            "category":{
                "id": 1,
                "name": "refresco"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(412, resp.status)
        Assertions.assertEquals("{\"message\":\"This product whit this name already exists\",\"code\":412}".trimIndent(),
            resp.contentAsString)
    }

    @Test
    fun editProductHappyPathTest() {
        //prerequisitos..
        val categrory = Category(1L, "Serie")
        entityManager.persist(categrory)
        entityManager.persist(Product(1L,"Fanta","Gaseosa",categrory))

        val body = """
           {
            "id": 1,
            "name": "Fanta",
            "branch": "Gaseosa",
            "category":{
                "id": 1,
                "name": "refresco"
            }
            }
        """.trimIndent()

        val request = MockMvcRequestBuilders
            .put("/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)

        val response = mocMvc.perform(request)
        val resp = response.andReturn().response
        //println(resp.contentAsString)
        Assertions.assertEquals(200, resp.status)
    }
}