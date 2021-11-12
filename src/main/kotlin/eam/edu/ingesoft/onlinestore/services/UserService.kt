package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.entities.User
import eam.edu.ingesoft.onlinestore.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var entityManager: EntityManager

    fun createUser(user: User){
        val userById = userRepository.find(user.id)

        if(userById != null){
            throw BusinessException("This person already exists")
        }
        userRepository.create(user)
    }

    fun editUser(user: User){
        userRepository.find(user.id) ?: BusinessException("This user does not exists")
        userRepository.update(user)
    }
}