import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.7.20"

    id("org.springframework.boot") version "2.6.2" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.2.1"
    id("io.freefair.lombok") version "5.3.0"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    // jpa에 noarg 생성자 생성 플러그인
    kotlin("plugin.jpa") version kotlinVersion
    // kotlin class 중 open이 필요한 클래스(entity)에 적용
    kotlin("plugin.allopen") version kotlinVersion
    // spring aop등 open이 필요한 클래스에 적용
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.lombok") version kotlinVersion
}

val buildPhase: String by extra(System.getProperty("build.phase", "local"))
val encryptorPassword: String by extra(System.getProperty("encryptorPassword", ""))

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "kr.mashup.branding"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "org.jetbrains.kotlin.plugin.lombok")

    java.sourceCompatibility = JavaVersion.VERSION_11

    tasks {
        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }

        test {
            useJUnitPlatform()
        }
    }
}