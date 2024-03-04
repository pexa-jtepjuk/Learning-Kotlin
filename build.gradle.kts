plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.register("fib"){
    description = "Calculate Fibo"

    val count = properties["count"]?.toString()?.toInt() ?: 10

    doLast {
        val fibo = generateSequence(1 to 1) { it.second to it.first + it.second }
            .map { it.first }
            .take(count)
            .toList()
        println(fibo)
    }
}

tasks.register<Fib>("fib2"){
    count.set(
        properties["count"]?.toString()?.toInt() ?: 10
    )
}

abstract class Fib : DefaultTask() {
    @get:Input
    abstract val count : Property<Int>

    @TaskAction
    fun fib() {
        if(count.get() < 0) {
            throw IllegalArgumentException("Count must be positive")
        }

        val fibo = generateSequence(1 to 1) { it.second to it.first + it.second }
            .map { it.first }
            .take(count.get())
            .toList()
        println(fibo)
    }
}