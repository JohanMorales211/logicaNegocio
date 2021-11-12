package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.entities.Product
import eam.edu.ingesoft.onlinestore.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var entityManager: EntityManager

    /**
     * Agregar product
     * 1. crear producto, no pueden haber dos productos con el mismo nombre
     * 2. editar producto. verificar que si se edita el nombre se valide la restriccion del punto a
     */
    fun createProduct(product: Product) {
        val productFind = productRepository.listNames(product)

        if (productFind != null) {
            throw BusinessException("This product whit this name already exists")
        }

        productRepository.create(product)
    }

    fun editProduct(product: Product) {
        val productById = productRepository.find(product.id) ?: throw BusinessException("This product whit this name does not exists")
        productRepository.update(product)
    }

    fun listCategoryByProduct(id: Long) = productRepository.findByCategory(id)

}