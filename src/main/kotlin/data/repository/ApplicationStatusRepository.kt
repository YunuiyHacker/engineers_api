package yunuiy_hacker.ryzhaya_tetenka.data.repository

import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import yunuiy_hacker.ryzhaya_tetenka.domain.model.ApplicationStatus

class ApplicationStatusRepository {

    fun getAllApplicationStatus(): List<ApplicationStatus> {
        val applicationStatuses = mutableListOf<ApplicationStatus>()

        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.selectAll()
                .orderBy(yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.id, SortOrder.ASC).forEach {
                applicationStatuses.add(
                    ApplicationStatus(
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.id],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.title],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.normalizedTitle]
                    )
                )
            }
        }

        return applicationStatuses
    }

    fun insertApplicationStatus(applicationStatus: ApplicationStatus) {
        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.ApplicationStatus.insert {
                it[title] = applicationStatus.title
                it[normalizedTitle] = applicationStatus.normalizedTitle
            }
        }
    }
}