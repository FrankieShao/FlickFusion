rootProject.name = "FlickFusion"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":modules:common-ui")
include(":modules:util")
include(":modules:core:domain")
include(":modules:core:data")
include(":modules:core:service")
include(":modules:framework:network")
include(":modules:framework:persist")
include(":modules:feature:main")
include(":modules:feature:mine")
include(":modules:feature:search")
include(":modules:feature:detail")
include(":modules:feature:favorite")
include(":modules:test-base")
