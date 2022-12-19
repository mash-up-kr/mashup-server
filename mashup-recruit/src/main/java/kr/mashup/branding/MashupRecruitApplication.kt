package kr.mashup.branding

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MashupRecruitApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application,recruit")
    SpringApplication.run(MashupRecruitApplication::class.java, *args)
}