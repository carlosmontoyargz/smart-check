package com.mino.smartcheck.data;

import com.mino.smartcheck.model.HorasTrabajo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

/**
 * @author Carlos Montoya
 * @since 24/11/2019
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HorasTrabajoDao
{
	private final EntityManager entityManager;

	public Long findTotal(String field, LocalDate periodoInicio) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<HorasTrabajo> horasTrabajo = cq.from(HorasTrabajo.class);
		cq.multiselect(
				horasTrabajo.get("fechaInicio"),
				cb.sum(horasTrabajo.get(field)));
		cq.where(
				cb.equal(horasTrabajo.get("fechaInicio"), periodoInicio));
		cq.groupBy(horasTrabajo.get("fechaInicio"));
		return (Long) entityManager.createQuery(cq).getSingleResult()[1];
	}
}
