package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.User
import eam.edu.ingesoft.onlinestore.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @PostMapping
    fun createStore(@RequestBody user: User) {
        userService.createUser(user)
    }

    @PutMapping("/{id}")
    fun editUser(@PathVariable id: String, @RequestBody user: User) {
        user.id = id
        userService.editUser(user)
    }
}