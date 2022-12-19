package kr.mashup.branding.ui

import kr.mashup.branding.service.ServerTimeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PingController(
    private val serverTimeService: ServerTimeService
) {

    @GetMapping("/ping")
    fun ping(): String =
        "MashUp-Recruit!"

    @GetMapping("/v2/ping")
    fun pingV2(): Boolean =
        true

    @GetMapping("/time")
    fun currentServerTime(): LocalDateTime =
        serverTimeService.currentServerTimeByProfile
}