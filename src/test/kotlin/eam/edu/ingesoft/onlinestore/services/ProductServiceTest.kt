package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
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
class ProductServiceTest {

    @Autowired
    lateinit var productService: ProductService

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun createProductHappyPathTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        productService.createProduct(Product(5L,"YeyeCoffe","CocaCola",category))

        val productToAssert = entityManager.find(Product::class.java, 5L)
        Assertions.assertNotNull(productToAssert)
        Assertions.assertEquals("YeyeCoffe", productToAssert.name)
    }

    @Test
    fun createProductRepeatedNameTest() {
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        productService.createProduct(Product(5L,"YeyeCoffe","CocaCola",category))

        val exception = Assertions.assertThrows(
            BusinessException::class.java
        ) {
            productService.createProduct(Product(5L,"YeyeCoffe","CocaCola",category))
        }

        Assertions.assertEquals("This product whit this name already exists", exception.message)
    }

    @Test
    fun editProductHappyPathTest(){
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        productService.createProduct(Product(5L,"YeyeCoffe","CocaCola",category))

        val product = entityManager.find(Product::class.java, 5L)
        product.name = "YeyeCoffe_V2"

        productService.editProduct(product)

        val productAssert = entityManager.find(Product::class.java, 5L)
        Assertions.assertEquals("YeyeCoffe_V2", productAssert.name)
    }

    @Test
    fun productNotExistsTest(){
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)

        val exception = Assertions.assertThrows(
            BusinessException::class.java,
            { productService.editProduct(Product(5L,"YeyeCoffe","CocaCola",category)) }
        )
        Assertions.assertEquals("This product whit this name does not exists", exception.message)
    }

    @Test
    fun listCategoryByProduct(){
        val category = Category(10L,"Gasimba")
        entityManager.persist(category)
        entityManager.persist(Product(2L, "YeyeCoffe_Lite","CocaCola",category))
        entityManager.persist(Product(3L, "YeyeCoffe_Sin_Azucar","CocaCola",category))
        entityManager.persist(Product(4L, "YeyeCoffe_Sin_Azucar_v2","CocaCola",category))

        val categorys = productService.listCategoryByProduct(2L)

        Assertions.assertEquals(4,categorys.size)
    }
}