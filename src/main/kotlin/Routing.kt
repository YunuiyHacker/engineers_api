package yunuiy_hacker.ryzhaya_tetenka

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import yunuiy_hacker.ryzhaya_tetenka.data.repository.ApplicationStatusRepository
import yunuiy_hacker.ryzhaya_tetenka.data.repository.MasterRepository
import yunuiy_hacker.ryzhaya_tetenka.data.repository.UserRepository
import yunuiy_hacker.ryzhaya_tetenka.domain.model.ApplicationStatus
import yunuiy_hacker.ryzhaya_tetenka.domain.model.Master
import yunuiy_hacker.ryzhaya_tetenka.domain.model.User

fun Application.configureRouting(
    applicationStatusRepository: ApplicationStatusRepository,
    userRepository: UserRepository,
    masterRepository: MasterRepository
) {
    routing {
        authenticate("auth-basic") {
            staticResources("static", "static")

            get("/") {
                call.respondText("Engineers API")
            }

            //application_statuses
            route("/application_statuses") {
                get {
                    call.respond(applicationStatusRepository.getAllApplicationStatus())
                }

                post {
                    try {
                        val applicationStatus = call.receive<ApplicationStatus>()
                        call.respond(applicationStatusRepository.insertApplicationStatus(applicationStatus))
                        call.respond(HttpStatusCode.OK)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }

            //users
            route("/users") {
                get {
                    call.respond(userRepository.getAllUsers())
                }

                post {
                    try {
                        val user: User = call.receive<User>()
                        val createdUser = userRepository.insertUser(user)
                        call.respond(HttpStatusCode.OK, createdUser ?: User())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                put {
                    try {
                        val user: User = call.receive<User>()
                        val updatedUser = userRepository.updateUser(user)
                        call.respond(HttpStatusCode.OK, updatedUser ?: User())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }

            get("/users/{userId}") {
                val userId = call.parameters["userId"]!!.toInt()

                call.respond(userRepository.getUserById(userId) ?: User())
            }

            get("/users/{login}/{password}") {
                val login = call.parameters["login"]!!.toString()
                val password = call.parameters["password"]!!.toString()

                call.respond(userRepository.getUserByLoginAndPassword(login, password) ?: User())
            }

            delete("/users/{userId}") {
                try {
                    val userId = call.parameters["userId"]!!.toInt()
                    userRepository.deleteUserById(userId)
                    call.respond(HttpStatusCode.OK)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            //masters
            route("/masters") {
                get {
                    call.respond(masterRepository.getAllMasters())
                }

                post {
                    try {
                        val master: Master = call.receive<Master>()
                        val createdMaster = masterRepository.insertMaster(master)
                        call.respond(HttpStatusCode.OK, createdMaster ?: Master())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }

                put {
                    try {
                        val master: Master = call.receive<Master>()
                        val updatedMaster = masterRepository.updateMaster(master)
                        call.respond(HttpStatusCode.OK, updatedMaster ?: Master())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
            }

            delete("/masters/{masterId}") {
                try {
                    val masterId = call.parameters["masterId"]!!.toInt()
                    masterRepository.deleteMasterById(masterId)
                    call.respond(HttpStatusCode.OK)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}
