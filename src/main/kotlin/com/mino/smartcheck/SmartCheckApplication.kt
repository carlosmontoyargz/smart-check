package com.mino.smartcheck

import com.mino.smartcheck.config.SmartCheckProperties
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableConfigurationProperties(SmartCheckProperties::class)
class SmartCheckApplication

fun main(args: Array<String>) {
	SpringApplication.run(SmartCheckApplication::class.java, *args)
}

@Component
class Runner
	@Autowired constructor(val smartCheckProperties: SmartCheckProperties):
		CommandLineRunner
{
	private var log = LogManager.getLogger()

	override fun run(vararg args: String?) {
		log.info("Secret key: {}", smartCheckProperties.secretKey)
	}
}
