package kr.mashup.branding.ui.applicant.vo

import kr.mashup.branding.domain.applicant.Applicant
import kr.mashup.branding.domain.applicant.ApplicantStatus
import java.time.LocalDate


data class ApplicantResponse(
    val applicantId: Long,
    val email: String,
    val name: String,
    val phoneNumber: String,
    val birthdate: LocalDate,
    val department: String,
    val residence: String,
    val status: ApplicantStatus,
) {

    companion object {
        @JvmStatic
        fun from(applicant: Applicant): ApplicantResponse {
            return ApplicantResponse(
                applicant.applicantId,
                applicant.email,
                applicant.name,
                applicant.phoneNumber,
                applicant.birthdate,
                applicant.department,
                applicant.residence,
                applicant.status
            )
        }
    }
}