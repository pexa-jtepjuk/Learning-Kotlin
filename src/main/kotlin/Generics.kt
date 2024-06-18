package org.example

fun main(args: Array<String>) {
    display(Human)
    breed(Animal("Dog"))
}

//fun <T: Int?> display (item: T) {
//    println("$item")
//}

fun <T> display (item: T) where T: Mammal, T: Species {
    println("$item")
}


interface Mammal {
    fun lifespan(): Int
}

interface Species {
    fun name(): String
}

object Human: Mammal, Species {
    override fun lifespan(): Int {
        return 80
    }

    override fun name(): String {
        return "Homo Sapiens"
    }
}

fun breed(a: Animal<in String>) {
//    val x = a.produce()
    a.consume("F")
}

class Animal <T> (private val t: T) {
    fun produce(): T{
        return t
    }

    fun consume(input: T) {
        println("T: $t, input: $input")
    }
}





