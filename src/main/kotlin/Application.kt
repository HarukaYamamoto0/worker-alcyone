package com.harukadev

import com.harukadev.domain.commands.CommandRegistry
import com.harukadev.domain.commands.impl.PingCommand
import com.harukadev.domain.commands.impl.UserInfoCommand
import com.harukadev.domain.commands.impl.ReportMessageCommand
import com.harukadev.config.RedisConfig
import com.harukadev.plugins.configureHTTP
import com.harukadev.plugins.configureMonitoring
import com.harukadev.plugins.configureSerialization
import com.harukadev.workers.WorkerSupervisor
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.ApplicationStopped
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()

    val worker = WorkerSupervisor()

    monitor.subscribe(ApplicationStarted) {
        CommandRegistry.register(PingCommand)
        CommandRegistry.register(UserInfoCommand)
        CommandRegistry.register(ReportMessageCommand)

        launch {
            println("Worker started")
            worker.start()
        }
    }

    monitor.subscribe(ApplicationStopped) {
        RedisConfig.shutdown()
    }
}
