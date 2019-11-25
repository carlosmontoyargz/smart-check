package com.mino.smartcheck.controller

import com.mino.smartcheck.data.HorasTrabajoDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/horasTrabajo")
class HorasTrabajoController
	@Autowired constructor(
			val horasTrabajoDao: HorasTrabajoDao)
{
	@GetMapping("/search/findTotal")
	fun findTotal(@RequestParam(required = true) field: String): ResponseEntity<Long>
	{
		return ResponseEntity.ok(horasTrabajoDao
				.findTotal(field, LocalDate.now().withDayOfMonth(1)))
	}
}
