package kr.mashup.branding.ui.member.response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import kr.mashup.branding.domain.member.MemberGeneration;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberGenerationsResponse {

	private List<MemberGenerationResponse> memberGenerations;

	public static MemberGenerationsResponse of(List<MemberGeneration> memberGenerations) {
		List<MemberGenerationResponse> responses = memberGenerations.stream()
			.map(MemberGenerationResponse::of)
			.sorted(Comparator.comparing(MemberGenerationResponse::getNumber).reversed())
			.collect(Collectors.toList());
		return new MemberGenerationsResponse(responses);
	}
}
