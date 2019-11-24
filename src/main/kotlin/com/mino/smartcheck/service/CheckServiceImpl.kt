package com.mino.smartcheck.service

import com.mino.smartcheck.data.CheckRepository
import com.mino.smartcheck.model.SmartCheck
import com.mino.smartcheck.model.TipoCheck
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/*
@Service
class CheckServiceImpl
	@Autowired constructor(val checkRepository: CheckRepository)
	: CheckService
{
	override fun guardarCheck(): SmartCheck {
		val now = LocalDateTime.now()
		return checkRepository
				.save(SmartCheck().apply {
					fecha = now.toLocalDate()
					hora = now.toLocalTime()
					horaBase = null
					tipoCheck = TipoCheck.ENTRADA
				})
	}
}
*/
