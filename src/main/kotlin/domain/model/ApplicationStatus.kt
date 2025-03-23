package yunuiy_hacker.ryzhaya_tetenka.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationStatus(val id: Int = 0, val title: String = "", val normalizedTitle: String = "")
