package com.retrox

import com.google.gson.JsonParser
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Semaphore
import kotlin.concurrent.thread

fun main() {
    print("Hello ")

    val seam = Semaphore(0)

    println("Thread Start ")

    thread {
        seam.acquire()
        println("Thread END ")

    }



    var result: Any = ""

    println("Coroutine Launch")
    println(Thread.currentThread())
    GlobalScope.launch {
        println(Thread.currentThread())
        val def1 = async {
            delay(1000L)
            println(Thread.currentThread())
            println("def1 delayed 1000L")
            222
        }

        result = def1.await()

    }
    println("Coroutine Launched $result")


    println("Coroutine Launch Blocking")
    val res = runBlocking {
        val def1 = async {
            delay(1000L)
            println("def1 delayed 1000L")
            222
        }

        def1.await()
    }
    println("Coroutine Launch Block Run OK")

    println("Now you can End")
    seam.release()

    while (true) {

    }

}

fun <T> List<T>.print() {
    forEach {
        println(it)
    }
}


/**
 * list.add(xxxx)
 * list.remove(xxxx)
 */
fun <T> List<T>.doSomething(block : List<T>.() -> Unit) {
    block.invoke(this)
//    block()
}

fun <T> List<T>.doSomething2(block : (T) -> Unit) {
    block.invoke(get(0))
//    block()
}