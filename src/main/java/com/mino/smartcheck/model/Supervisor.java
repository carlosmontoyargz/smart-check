package com.mino.smartcheck.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author Carlos Montoya
 * @since 18/09/2019
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Supervisor extends Persona
{

}
