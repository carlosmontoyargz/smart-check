package com.mino.smartcheck.controller

import com.mino.smartcheck.data.CheckRepository
import com.mino.smartcheck.data.HorasTrabajoRepository
import com.mino.smartcheck.dto.StatisticsResult
import com.mino.smartcheck.model.TipoCheck.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/statistics")
class StatisticsController
	@Autowired constructor(
			val checkRepository: CheckRepository,
			val horasTrabajoRepository: HorasTrabajoRepository)
{
	@GetMapping("/all")
	fun findAllStatistics(): StatisticsResult {
		val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
		return StatisticsResult().apply {
			//minutosTrabajados = horasTrabajoRepository.findTotalMinutosTrabajados(firstDayOfMonth)
			minutosExtra = checkRepository.findTotalPositivo(SALIDA)
			minutosRetrasos = checkRepository.findTotalPositivo(ENTRADA)
		}
	}
}
