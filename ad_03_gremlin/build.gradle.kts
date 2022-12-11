plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.apache.tinkerpop:gremlin-driver:3.6.1+")

    testImplementation(kotlin("test-junit"))
}
