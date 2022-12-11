plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.postgresql:postgresql:42.5.1")

    testImplementation(kotlin("test-junit"))
}
