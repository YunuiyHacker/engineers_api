package yunuiy_hacker.ryzhaya_tetenka

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import yunuiy_hacker.ryzhaya_tetenka.data.repository.ApplicationStatusRepository
import yunuiy_hacker.ryzhaya_tetenka.data.repository.MasterRepository
import yunuiy_hacker.ryzhaya_tetenka.data.repository.UserRepository
import yunuiy_hacker.ryzhaya_tetenka.utils.Constants

val db: Database = Database.connect(
    url = "jdbc:postgresql://127.0.0.1:5432/engineers", user = "postgres", password = "Nurkaev777"
)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(Authentication) {
        basic("auth-basic") {
            realm = "Ktor Server"
            validate { credentials ->
                if (credentials.name == Constants.API_USER && credentials.password == Constants.API_PASSWORD) {
                    UserIdPrincipal(credentials.name)
                } else null
            }
        }
    }



    configureRouting(
        applicationStatusRepository = ApplicationStatusRepository(),
        userRepository = UserRepository(),
        masterRepository = MasterRepository()
    )
}
