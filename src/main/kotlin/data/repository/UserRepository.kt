package yunuiy_hacker.ryzhaya_tetenka.data.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import yunuiy_hacker.ryzhaya_tetenka.domain.model.User

class UserRepository {

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()

        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.User.selectAll()
                .orderBy(yunuiy_hacker.ryzhaya_tetenka.data.model.User.id, SortOrder.ASC).forEach {
                    users.add(
                        User(
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.id],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.name],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.login],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.password],
                            it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId] ?: 0
                        )
                    )
                }
        }

        return users
    }

    fun getUserById(id: Int): User? {
        var user: User? = null

        transaction {
            user = yunuiy_hacker.ryzhaya_tetenka.data.model.User.selectAll()
                .where { yunuiy_hacker.ryzhaya_tetenka.data.model.User.id eq id }.map {
                    User(
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.id],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.name],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.login],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.password],
                        it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId] ?: 0
                    )
                }.singleOrNull()
        }

        return user
    }

    fun getUserByLoginAndPassword(login: String, password: String): User? {
        var user: User? = null

        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.User.selectAll().where {
                yunuiy_hacker.ryzhaya_tetenka.data.model.User.login eq login and (yunuiy_hacker.ryzhaya_tetenka.data.model.User.password eq password)
            }.forEach {
                user = User(
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.id],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.name],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.login],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.password],
                    it[yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId] ?: 0
                )
            }
        }

        return user
    }

    fun insertUser(user: User): User? {
        var createdUser: User? = null

        transaction {
            val resultRow = yunuiy_hacker.ryzhaya_tetenka.data.model.User.insertReturning {
                it[surname] = user.surname
                it[name] = user.name
                it[lastname] = user.lastname
                it[login] = user.login
                it[password] = user.password
                it[masterId] = user.masterId
            }.singleOrNull()

            if (resultRow != null) {
                createdUser = User(
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.id],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.name],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.login],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.password],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId] ?: 0
                )
            }
        }

        return createdUser
    }

    fun updateUser(user: User): User? {
        var updatedUser: User? = null

        transaction {
            var resultRow = yunuiy_hacker.ryzhaya_tetenka.data.model.User.updateReturning(
                listOf(
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.id,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.name,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.login,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.password,
                    yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId
                ), { yunuiy_hacker.ryzhaya_tetenka.data.model.User.id eq user.id }) {
                it[surname] = user.surname
                it[name] = user.name
                it[lastname] = user.lastname
                it[login] = user.login
                it[password] = user.password
                it[masterId] = user.masterId
            }.singleOrNull()

            if (resultRow != null) {
                updatedUser = User(
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.id],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.surname],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.name],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.lastname],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.login],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.password],
                    resultRow[yunuiy_hacker.ryzhaya_tetenka.data.model.User.masterId] ?: 0
                )
            }
        }

        return updatedUser
    }

    fun deleteUserById(userId: Int) {
        transaction {
            yunuiy_hacker.ryzhaya_tetenka.data.model.User.deleteWhere { yunuiy_hacker.ryzhaya_tetenka.data.model.User.id eq userId }
        }
    }
}