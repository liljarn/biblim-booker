import com.google.protobuf.gradle.id

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.4"
}
val springAiVersion by extra("1.0.0-M8")

group = "ru.liljvrn"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.28.2"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.68.0"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc") { }
                id("grpckt") { }
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDir("src/main/proto")
        }
    }
}

dependencies {
    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Database
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.postgresql:postgresql")

    // S3
    implementation(platform("software.amazon.awssdk:bom:2.27.21"))
    implementation("software.amazon.awssdk:s3")

    // gRPC
    implementation("net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE")
    implementation(platform("io.grpc:grpc-bom:1.68.0"))
    implementation("io.grpc:grpc-api")
    implementation("io.grpc:grpc-core")
    implementation("io.grpc:grpc-protobuf")
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-stub")
    implementation("io.grpc:grpc-netty-shaded")
    implementation("com.google.protobuf:protobuf-java-util:4.28.2")
    implementation("com.google.protobuf:protobuf-kotlin:4.28.2")

    // Kafka
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    // Embedding
    implementation("org.springframework.ai:spring-ai-starter-vector-store-qdrant")
    implementation("org.springframework.ai:spring-ai-starter-model-ollama")

    // Serialization
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // AOP
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Metrics
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Swagger
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:$springAiVersion")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
