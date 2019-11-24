package com.mino.smartcheck.dto

open class JwtRequest {
	companion object {
		private const val serialVersionUID = 5926468583005150707L
	}

	var username: String? = null
	var password: String? = null
}
