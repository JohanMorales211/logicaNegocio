package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.ProductStore
import eam.edu.ingesoft.onlinestore.repositories.ProductStoreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class ProductStoreService {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var productStoreRepository: ProductStoreRepository

    fun createProductStore(productStore: ProductStore) {
        val productById = productStoreRepository.find(productStore.id)

        if (productById != null) {
            throw BusinessException("This product already exists on the store")
        }
        productStoreRepository.create(productStore)
    }

    fun decreaseStockProductStore(productStore: ProductStore, cantidadDisminuir : Double){
        productStoreRepository.find(productStore.id)?: throw BusinessException ("This product in this store does not exists")
        if (productStore.stock < cantidadDisminuir){
            throw BusinessException ("There is no stock")
        }
        val resultado = productStore.stock - cantidadDisminuir
        val productStoreFind = entityManager.find(ProductStore::class.java, productStore.id)
        productStoreFind.stock = resultado
        if (resultado >= 0 ){
            productStoreRepository.update(productStore)
        }
    }
}