package yunuiy_hacker.ryzhaya_tetenka.data.model

import org.jetbrains.exposed.sql.Table

object ApplicationStatus: Table("application_statuses") {
    val id = integer("id").autoIncrement()
    val title = text("title")
    val normalizedTitle = text("normalized_title")
}