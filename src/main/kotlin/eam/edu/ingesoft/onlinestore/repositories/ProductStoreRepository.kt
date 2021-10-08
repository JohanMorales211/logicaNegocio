package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.Product
import eam.edu.ingesoft.onlinestore.model.ProductStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ProductStoreRepository {
    @Autowired
    lateinit var em: EntityManager

    fun create(productStore: ProductStore){
        em.persist(productStore)
    }


    fun find(id: Long): ProductStore?{
        return em.find(ProductStore::class.java, id)
    }

    fun update(productStore: ProductStore) {
        em.merge(productStore)
    }

    fun delete(id: Long) {
        val productStore = find(id)
        if (productStore!=null) {
            em.remove(productStore)
        }
    }

    fun findByProduct(id: Long):List<ProductStore>{
        val query= em.createQuery("SELECT pro FROM ProductStore pro WHERE pro.id_producto =: id")
        query.setParameter("id", id)

        return query.resultList as List<ProductStore>
    }
}