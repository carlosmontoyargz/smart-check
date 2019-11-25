package com.mino.smartcheck.data;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
public class HorasTrabajoDaoTest
{
	@Autowired HorasTrabajoDao horasTrabajoDao;

	@Test
	public void findTotal()
	{
		Long total = horasTrabajoDao
				.findTotal("minutos", LocalDate.now().withDayOfMonth(1));
		log.info(total);
	}
}
