package com.mino.smartcheck

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SmartCheckApplication

fun main(args: Array<String>) {
	SpringApplication.run(SmartCheckApplication::class.java, *args)
}
