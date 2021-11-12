package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.entities.Category
import eam.edu.ingesoft.onlinestore.model.entities.Product
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun testCreate() {
        //prerequisitos
        //que la persona no exista
        val category = Category(10L,"Gasimba")

        //la ejecucion de la prueba.. llamar el metodo que estoy probando
        productRepository.create(Product(5L,"YeyeCoffe","CocaCola",category))

        //asersiones, o las verificaciones
        val product = entityManager.find(Product::class.java,  5L)
        Assertions.assertNotNull(product)
        Assertions.assertEquals(5L, product.id)
        Assertions.assertEquals("YeyeCoffe", product.name)
        Assertions.assertEquals("CocaCola", product.branch)
        Assertions.assertEquals(10L , product.category.id)
    }

    @Test
    fun testUpdate() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        entityManager.persist(Product(5L,"YeyeCoffe","CocaCola",category))

        //ejecutando...
        val product = entityManager.find(Product::class.java, 5L)
        product.name = "BebidaYoyo"

        productRepository.update(product)

        //assersiones
        val productAssert = entityManager.find(Product::class.java, 5L)
        Assertions.assertEquals("BebidaYoyo", productAssert.name)
    }

    @Test
    fun findTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        entityManager.persist(Product(5L,"YeyeCoffe","CocaCola",category))

        val prodruct = productRepository.find(5L)

        Assertions.assertNotNull(prodruct)
        Assertions.assertEquals("YeyeCoffe", prodruct?.name)
    }

    @Test
    fun testDelete() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        entityManager.persist(Product(5L,"YeyeCoffe","CocaCola",category))
        productRepository.delete(5L)

        val product = entityManager.find(Product::class.java, 5L)
        Assertions.assertNull(product)
    }

}