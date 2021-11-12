package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.Category
import eam.edu.ingesoft.onlinestore.services.CategoryService
import eam.edu.ingesoft.onlinestore.services.ProductStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService

    @PostMapping
    fun createCategory(@RequestBody category: Category) {
        categoryService.createCategory(category)
    }
}