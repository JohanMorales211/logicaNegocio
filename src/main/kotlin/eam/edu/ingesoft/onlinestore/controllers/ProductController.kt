package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.Product
import eam.edu.ingesoft.onlinestore.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    @PostMapping
    fun createProduct(@RequestBody product: Product) {
        productService.createProduct(product)
    }

    @PutMapping("/{id}")
    fun editLibro(@PathVariable id: Long, @RequestBody product: Product) {
        product.id = id
        productService.editProduct(product)
    }
}