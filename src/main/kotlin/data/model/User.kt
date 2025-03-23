package yunuiy_hacker.ryzhaya_tetenka.data.model

import org.jetbrains.exposed.sql.Table

object User : Table("users") {
    val id = integer("id").autoIncrement()
    val surname = text("surname")
    val name = text("name")
    val lastname = text("lastname")
    val login = text("login")
    val password = text("password")
    val masterId = integer("master_id").nullable()
}