package eam.edu.ingesoft.onlinestore.model

import eam.edu.ingesoft.onlinestore.model.City
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_tienda")
data class Store(
    @Id
    @Column(name = "id_tienda")
    val id: String,

    @Column(name = "direccion")
    var address: String,

    @Column(name = "nombre")
    var name: String,

    @ManyToOne
    @JoinColumn(name="id_ciudad")
    val city: City
) : Serializable
