plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.spotbugs") version "6.0.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

val fibCount: String by project

val kotlinCodeStyle = project.property("kotlin.code.style") as String

tasks.register("fib"){
    description = "Calculate Fibo"

    println("Kotlin Code Style is $kotlinCodeStyle")

    doLast {
        val fibo = generateSequence(1 to 1) { it.second to it.first + it.second }
            .map { it.first }
            .take(fibCount.toInt())
            .toList()
        println(fibo)
    }
}

tasks.register<Fib>("fib2"){
    count.set(
        properties["count"]?.toString()?.toInt() ?: 10
    )
}

tasks.register("qualityCheck") {                       
    description = "Runs checks (excluding tests)."      
    dependsOn(tasks.classes, tasks.spotbugsMain)        
    dependsOn(tasks.testClasses, tasks.spotbugsTest)   
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

class ApplesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("apples") {
            description = "A simple message"
            group = "custom plugin"

            doLast{
                println("Hello world")
            }
        }
    }
}

apply<ApplesPlugin>()

val myBuildGroup = "my app build"               

tasks.register<TaskReportTask>("tasksAll") {    
    group = myBuildGroup
    description = "Show additional tasks."
    setShowDetail(true)
}

tasks.named<TaskReportTask>("tasks") {          
    displayGroup = myBuildGroup
}


