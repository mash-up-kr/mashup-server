package kr.mashup.branding.ui.team

import kr.mashup.branding.domain.team.Team

data class TeamResponse(
    val teamId: Long,
    val name: String
) {

    companion object {
        @JvmStatic
        fun from(team: Team): TeamResponse {
            return TeamResponse(team.teamId, team.name)
        }
    }
}