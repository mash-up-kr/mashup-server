apply(from = "jib.gradle")

dependencies {
    val javaJwtVersion: String by project
    val swaggerVersion: String by project
    val commonsTextVersion: String by project

    implementation(project(":mashup-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.281")
    implementation("ca.pjer:logback-awslogs-appender:1.4.0")

    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("io.springfox:springfox-boot-starter:$swaggerVersion")
    implementation("org.apache.commons:commons-text:$commonsTextVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks {
    jar {
        enabled = false
    }

    bootJar {
        enabled = true
    }
}
