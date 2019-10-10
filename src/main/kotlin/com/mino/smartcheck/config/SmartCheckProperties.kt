package com.mino.smartcheck.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.crypto.SecretKey

@ConfigurationProperties(prefix = "jwt")
class SmartCheckProperties(var secretKey: String = "")
