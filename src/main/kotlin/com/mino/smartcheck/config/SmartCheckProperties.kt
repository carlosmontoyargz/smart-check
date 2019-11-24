package com.mino.smartcheck.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class SmartCheckProperties(var secret: String = "")
