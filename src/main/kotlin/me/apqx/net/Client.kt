package me.apqx.net

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

fun main(args: Array<String>) = runBlocking {
    var client: Client? = null
    try {
        client = Client("localhost", 1234)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        client?.close()
    }
}

class Client(ip: String, port: Int) {
    private val netTransfer: NetTransfer

    init {
        val socketChannel = SocketChannel.open(InetSocketAddress(ip, port))
        netTransfer = NetTransfer(socketChannel, "c")
        println("client connect $ip:$port")
        runBlocking {
            doSend()
        }
    }

    suspend fun doSend() {
        repeat(5) {
            netTransfer.writeCmd(byteArrayOf(it.toByte(), 0x01, 0x01))
            delay(1000)
        }
    }

    suspend fun close() {
        netTransfer.close()
    }

}