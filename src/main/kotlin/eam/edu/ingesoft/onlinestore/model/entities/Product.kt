package eam.edu.ingesoft.onlinestore.model.entities

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_products")
data class Product(
    @Id
    @Column(name = "id_producto")
    var id: Long,

    @Column(name = "nombre")
    var name: String,

    @Column(name = "marca")
    var branch: String,

    @ManyToOne
    @JoinColumn(name="id_categoria")
    val category: Category
) : Serializable
