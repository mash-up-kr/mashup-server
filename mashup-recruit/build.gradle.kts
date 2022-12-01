apply(from = "jib.gradle")

dependencies {
    implementation(project(":mashup-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:3.18.3")
    implementation("com.google.api-client:google-api-client:1.32.1")
    implementation("com.google.api-client:google-api-client-jackson2:1.32.1")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.281")
    implementation("ca.pjer:logback-awslogs-appender:1.4.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.apache.commons:commons-text:1.9")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    jar {
        enabled = false
    }

    bootJar {
        enabled = true
    }

}
