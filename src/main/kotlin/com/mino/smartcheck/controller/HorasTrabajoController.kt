package com.mino.smartcheck.controller

import com.mino.smartcheck.data.HorasTrabajoDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/horasTrabajo")
@CrossOrigin(origins = ["*"])
class HorasTrabajoController
	@Autowired constructor(
			val horasTrabajoDao: HorasTrabajoDao)
{
	@GetMapping("/search/findTotal")
	fun findTotal(@RequestParam(required = true) field: String): ResponseEntity<Long> {
		return ResponseEntity.ok(horasTrabajoDao
				.findTotal(field, LocalDate.now().withDayOfMonth(1)))
	}
}
