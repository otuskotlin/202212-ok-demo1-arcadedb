
rootProject.name = "otus-202212-demo2-adcadedb"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.squareup.sqldelight") {
                useModule("com.squareup.sqldelight:gradle-plugin:1.5.4")
            }
        }
    }

    plugins {
        val kotlinVersion: String by settings

        kotlin("jvm") version kotlinVersion

    }
}

include("ad_01_postgres")
include("ad_02_redis")
include("ad_03_gremlin")
