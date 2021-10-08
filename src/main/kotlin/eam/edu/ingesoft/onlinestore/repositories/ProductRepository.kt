package eam.edu.ingesoft.onlinestore.repositories

import eam.edu.ingesoft.onlinestore.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
@Transactional
class ProductRepository {

    @Autowired
    lateinit var em: EntityManager

    fun create(product: Product){
        em.persist(product)
    }

    fun find(id: Long): Product?{
        return em.find(Product::class.java, id)
    }

    fun listNames(product: Product): List<String> {
        return listOf(product.name)
    }

    fun update(product: Product) {
        em.merge(product)
    }

    fun delete(id: Long) {
        val product = find(id)
        if (product!=null) {
            em.remove(product)
        }
    }

    fun findByCategory(id: Long): List<Product> {
        val query = em.createQuery("SELECT pro FROM Product pro WHERE pro.category.id =: id")
        query.setParameter("id", id)

        return query.resultList as List<Product>
    }
}