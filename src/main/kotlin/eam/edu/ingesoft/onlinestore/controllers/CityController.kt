package eam.edu.ingesoft.onlinestore.controllers

import eam.edu.ingesoft.onlinestore.model.entities.City
import eam.edu.ingesoft.onlinestore.services.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cities")
class CityController {

    @Autowired
    lateinit var cityService: CityService

    @PostMapping
    fun createCategory(@RequestBody city: City) {
        cityService.createCategory(city)
    }
}