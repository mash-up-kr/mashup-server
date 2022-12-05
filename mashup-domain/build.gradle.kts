dependencies {
    val mysqlVersion: String by project
    val jasyptVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("com.h2database:h2")
    implementation("mysql:mysql-connector-java:$mysqlVersion")
    // 찾을 수 없음 오류 - cloud watch만 쓰는 중
    //implementation "com.github.napstr:logback-discord-appender:$discordLogbackAppenderVersion"

    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:$jasyptVersion")

    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("jakarta.annotation:jakarta.annotation-api")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    jar {
        enabled = true
    }
    bootJar {
        enabled = false
    }
}