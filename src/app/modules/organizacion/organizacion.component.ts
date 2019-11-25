import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {HorasTrabajoService} from "../../services/horasTrabajo.service";
import {OrganizacionService} from "../../services/organizacion.service";
import { ModalDirective } from 'ngx-bootstrap';

@Component({
	templateUrl: 'organizacion.component.html'
})
export class OrganizacionComponent implements OnInit {

	constructor(private authenticationService: AuthenticationService,
              private organizacionService: OrganizacionService,
              private horasTrabajoService: HorasTrabajoService) {}

	user = this.authenticationService.currentUserValue;

  @ViewChild('successModal', {static: false}) public successModal: ModalDirective;

	totalMinutos = '';
	totalExtras = '';
	totalRetrasos = '';
	totalEntradasTemprano = '';
	totalSalidasTemprano = '';
  organizacion: string = '';
  orgHoraEntrada: string = '';
  orgHoraSalida: string = '';

	ngOnInit(): void {
	  this.organizacion = this.authenticationService.currentUserValue.organizacionNombre;
	  this.organizacionService
      .obtenerOrganizacion(this.organizacion)
      .subscribe(org => {
        this.orgHoraEntrada = org.horaEntrada;
        this.orgHoraSalida = org.horaSalida;
      });

	  this.horasTrabajoService
      .obtenerTotal("minutos")
      .subscribe(
        total => { this.totalMinutos = OrganizacionComponent.parseMinutes(total) },
        error => { console.log(error) });
    this.horasTrabajoService
      .obtenerTotal("extras")
      .subscribe(
        total => { this.totalExtras = OrganizacionComponent.parseMinutes(total) },
        error => { console.log(error) });
    this.horasTrabajoService
      .obtenerTotal("retrasos")
      .subscribe(
        total => { this.totalRetrasos = OrganizacionComponent.parseMinutes(total) },
        error => { console.log(error) });
    this.horasTrabajoService
      .obtenerTotal("salidasTemprano")
      .subscribe(
        total => { this.totalSalidasTemprano = OrganizacionComponent.parseMinutes(total) },
        error => { console.log(error) });
    this.horasTrabajoService
      .obtenerTotal("entradasTemprano")
      .subscribe(
        total => { this.totalEntradasTemprano = OrganizacionComponent.parseMinutes(total) },
        error => { console.log(error) });
  }

  private static parseMinutes(min: number): string {
    if (min < 0) min = -min;
    let horas = Math.trunc(min / 60);
    let minutes = Math.trunc(min % 60);
    return `${String(horas)}:${String(minutes).padStart(2, '0')}`
  }

  enviarEvaluacion() {
    // this.respuestasCorrectas = this.calcularRespuestasCorrectas();
    // let p = new Puntaje();
    // p.leccion = this.leccion._links.self.href;
    // p.usuario = this.authenticationService.currentUserValue.username;
    // p.puntaje = this.respuestasCorrectas;
    // this.puntajeService.postPuntaje(p).subscribe(
    //   data => { this.successModal.show() },
    //   error => { console.log(error)}
    //);
    this.successModal.hide();
  }
}
