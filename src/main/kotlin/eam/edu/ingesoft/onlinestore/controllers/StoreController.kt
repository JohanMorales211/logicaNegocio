package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.Store
import eam.edu.ingesoft.onlinestore.services.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stores")
class StoreController {
    @Autowired
    lateinit var storeService: StoreService

    @PostMapping
    fun createStore(@RequestBody store: Store) {
        storeService.createStore(store)
    }

    @PutMapping("/{id}")
    fun editStore(@PathVariable id: String, @RequestBody store: Store) {
        store.id = id
        storeService.editStore(store)
    }
}