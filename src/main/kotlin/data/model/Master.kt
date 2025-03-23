package yunuiy_hacker.ryzhaya_tetenka.data.model

import org.jetbrains.exposed.sql.Table

object Master : Table("masters") {
    val id = integer(name = "id").autoIncrement()
    val title = text("title")
    val titleClarifying = text("title_clarifying")
}