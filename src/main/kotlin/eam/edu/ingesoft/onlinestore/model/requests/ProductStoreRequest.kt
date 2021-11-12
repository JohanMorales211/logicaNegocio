package eam.edu.ingesoft.onlinestore.model.requests

data class ProductStoreRequest(
    var idProduct: Long,
    var stock: Double,
    var idstore:String,
    var price: Double
)
