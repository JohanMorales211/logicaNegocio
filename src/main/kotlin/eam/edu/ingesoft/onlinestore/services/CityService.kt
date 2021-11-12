package eam.edu.ingesoft.onlinestore.services

import eam.edu.ingesoft.onlinestore.exceptions.BusinessException
import eam.edu.ingesoft.onlinestore.model.entities.City
import eam.edu.ingesoft.onlinestore.repositories.CityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityService {

    @Autowired
    lateinit var cityRepository: CityRepository

    fun createCategory(city: City){
        val cityById = cityRepository.find(city.id)

        if(cityById != null){
            throw BusinessException("This city already exists")
        }
        cityRepository.create(city)
    }
}