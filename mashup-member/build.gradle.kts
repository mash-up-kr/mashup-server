apply(from = "jib.gradle")

dependencies {
    val javaJwtVersion: String by project
    val swaggerVersion: String by project
    val commonsTextVersion: String by project

    implementation(project(":mashup-domain"))
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.281")
    implementation("ca.pjer:logback-awslogs-appender:1.4.0")

    // util
    implementation("org.apache.commons:commons-text:$commonsTextVersion")

    // auth
    implementation("com.auth0:java-jwt:$javaJwtVersion")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // swagger
    implementation("io.springfox:springfox-boot-starter:$swaggerVersion")

    //test
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
