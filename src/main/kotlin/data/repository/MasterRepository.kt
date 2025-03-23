package yunuiy_hacker.ryzhaya_tetenka.data.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import yunuiy_hacker.ryzhaya_tetenka.domain.model.Master
import yunuiy_hacker.ryzhaya_tetenka.domain.model.User

class MasterRepository {

    fun getAllMasters(): List<Master> {
        val masters = mutableListOf<Master>()

        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.Master.selectAll()
                .orderBy(yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id, SortOrder.ASC).forEach {
                    masters.add(
                        Master(
                            id = it[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id],
                            title = it[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.title],
                            titleClarifying = it[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.titleClarifying]
                        )
                    )
                }
        }

        return masters
    }

    fun insertMaster(master: Master): Master? {
        var createdMaster: Master? = null

        transaction {
            val resultRow = yunuiy_hacker.ryzhaya_tetenka.data.model.Master.insert {
                it[title] = master.title
                it[titleClarifying] = master.titleClarifying
            }

            if (resultRow != null) {
                createdMaster = Master(
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.title],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.titleClarifying]
                )
            }
        }

        return createdMaster
    }

    fun updateMaster(master: Master): Master? {
        var updatedMaster: Master? = null

        transaction {
            val resultRow = yunuiy_hacker.ryzhaya_tetenka.data.model.Master.updateReturning(
                listOf(
                    yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.Master.title,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.Master.titleClarifying
                ),
                {
                    it[title] = master.title
                    it[titleClarifying] = master.titleClarifying
                }).singleOrNull()

            if (resultRow != null) {
                updatedMaster = Master(
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.title],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.Master.titleClarifying]
                )
            }
        }

        return updatedMaster
    }

    fun deleteMasterById(masterId: Int) {
        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.Master.deleteWhere { yunuiy_hacker.ryzhaya_tetenka.data.model.Master.id eq masterId }
        }
    }

}