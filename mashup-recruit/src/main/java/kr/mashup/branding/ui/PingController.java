package kr.mashup.branding.ui;

import kr.mashup.branding.service.ServerTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PingController {

	private final ServerTimeService serverTimeService;
	
	@GetMapping("/ping")
	public String ping() {
		return "MashUp-Recruit!";
	}

	@GetMapping("/v2/ping")
	public Boolean pingV2() {
		return true;
	}

	@GetMapping("/time")
	public LocalDateTime getCurrentServerTime(){
		return serverTimeService.getCurrentServerTimeByProfile();
	}
}
