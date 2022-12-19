package kr.mashup.branding.ui.applicant.vo

data class LoginResponse(
    val accessToken: String,
    val applicant: ApplicantResponse
) {

    companion object {
        @JvmStatic
        fun of(
            accessToken: String,
            applicantResponse: ApplicantResponse
        ): LoginResponse {
            return LoginResponse(accessToken, applicantResponse)
        }
    }
}