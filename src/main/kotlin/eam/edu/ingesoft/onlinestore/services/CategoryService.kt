package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.entities.Category
import eam.edu.ingesoft.onlinestore.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryService {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun createCategory(category: Category){
        val categoryById = categoryRepository.find(category.id)

        if(categoryById != null){
            throw BusinessException("This category already exists")
        }
        categoryRepository.create(category)
    }
}