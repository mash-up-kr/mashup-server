package kr.mashup.branding.ui.applicant;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.mashup.branding.domain.applicant.ApplicantStatus;
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
}
