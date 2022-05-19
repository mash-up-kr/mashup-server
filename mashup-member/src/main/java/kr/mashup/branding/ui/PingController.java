package kr.mashup.branding.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
	@GetMapping("/ping")
	public boolean ping() {
		return true;
	}

	@GetMapping("/v2/ping")
	public boolean pingV2() {
		return true;
	}
}
