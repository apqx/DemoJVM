package me.apqx.net

import kotlinx.coroutines.experimental.*
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel

fun main(args: Array<String>) = runBlocking {
    var server: Server? = null
    try {
        server = Server(1234)
    } catch (e: Exception) {
        e.printStackTrace()

    } finally {
//        server?.close()
        delay(100000)
    }
}

class Server(port: Int) {
    private val netTransfer: NetTransfer

    init {
        val serverSocketChannel = ServerSocketChannel.open()
        serverSocketChannel.socket().bind(InetSocketAddress(port))
        println("server listener $port")
        netTransfer = NetTransfer(serverSocketChannel.accept(), "s")
        netTransfer.setOnCmdReceiveListener{
            GlobalScope.launch(Dispatchers.Default) {
                netTransfer.writeCmd(byteArrayOf((it[0] * 10).toByte(), it[1], it[2]))
            }
        }
    }

    suspend fun close() {
        netTransfer.close()
    }
}