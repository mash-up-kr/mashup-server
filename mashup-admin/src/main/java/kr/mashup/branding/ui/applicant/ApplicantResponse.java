package kr.mashup.branding.ui.applicant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.mashup.branding.domain.applicant.Applicant;
import kr.mashup.branding.domain.applicant.ApplicantStatus;
import kr.mashup.branding.domain.application.Application;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantResponse {
    private Long applicantId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthdate;
    private String department;
    private String residence;
    private ApplicantStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ApplicantResponse from(Applicant applicant){
        return new ApplicantResponse(
            applicant.getApplicantId(),
            applicant.getName(),
            applicant.getEmail(),
            applicant.getPhoneNumber(),
            applicant.getBirthdate(),
            applicant.getDepartment(),
            applicant.getResidence(),
            applicant.getStatus(),
            applicant.getCreatedAt(),
            applicant.getUpdatedAt());
    }
}
