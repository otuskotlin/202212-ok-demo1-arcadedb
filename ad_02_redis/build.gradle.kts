plugins {
    kotlin("jvm")
}

dependencies {
    implementation("redis.clients:jedis:4.3.0")

    testImplementation(kotlin("test-junit"))
}
