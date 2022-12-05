import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.7.21"

    id("org.springframework.boot") version "2.7.3" apply false
    id("io.spring.dependency-management") version "1.1.0"
    id("com.google.cloud.tools.jib") version "3.2.1"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    // jpa에 noarg 생성자 생성 플러그인
    kotlin("plugin.jpa") version kotlinVersion
    // kotlin class 중 open이 필요한 클래스(entity)에 적용
    kotlin("plugin.allopen") version kotlinVersion
    // spring aop등 open이 필요한 클래스에 적용
    kotlin("plugin.spring") version kotlinVersion
    // 롬복 플러그인 설정
    kotlin("plugin.lombok") version kotlinVersion
    id("io.freefair.lombok") version "5.3.0"
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
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.jetbrains.kotlin.plugin.lombok")

    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
    }

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

    // 롬복 설정 (https://kotlinlang.org/docs/lombok.html)
    kapt {
        keepJavacAnnotationProcessors = true
    }
}