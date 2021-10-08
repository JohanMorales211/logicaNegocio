package eam.edu.ingesoft.onlinestore.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "tbl_categorias")
data class Category(
    @Id
    @Column(name = "id_categoria")
    val id: Long,

    @Column(name = "nombre")
    var name: String,
) : Serializable
