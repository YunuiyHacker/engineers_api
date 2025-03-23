package yunuiy_hacker.ryzhaya_tetenka.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val surname: String = "",
    val name: String = "",
    val lastname: String = "",
    val login: String = "",
    val password: String = "",
    val masterId: Int = 0
)
