import {Component, OnInit, ViewChild} from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {HorasTrabajoService} from "../../services/horasTrabajo.service";
import {OrganizacionService} from "../../services/organizacion.service";
import { ModalDirective } from 'ngx-bootstrap';
import {Organizacion} from "../../models/smart-check";
import { FormGroup, Validators, FormBuilder , FormControl} from '@angular/forms';

@Component({
	templateUrl: 'organizacion.component.html'
})
export class OrganizacionComponent implements OnInit {

	constructor(private authenticationService: AuthenticationService,
              private organizacionService: OrganizacionService,
              private horasTrabajoService: HorasTrabajoService,
              private formBuilder: FormBuilder) {}

	user = this.authenticationService.currentUserValue;

  @ViewChild('successModal', {static: false}) public successModal: ModalDirective;

	totalMinutos = '';
	totalExtras = '';
	totalRetrasos = '';
	totalEntradasTemprano = '';
	totalSalidasTemprano = '';
  organizacionStr: string = '';

  organizacion: Organizacion;

  loading = false;
  submitted = false;
  returnUrl: string;

  horaEntrada = new FormControl('');
  horaSalida = new FormControl('');
  horaEntradaStr = '';
  horaSalidaStr = '';

	ngOnInit(): void {
	  this.organizacionService
      .obtenerOrganizacion(this.authenticationService.currentUserValue.organizacionNombre)
      .subscribe(org => {
        console.log("organizacion");
        console.log(org);
        this.organizacion = org;
        this.horaEntradaStr = org.horaEntrada;
        this.horaSalidaStr = org.horaSalida;
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

  mostrarModal() {
    this.successModal.show();
    this.horaEntrada.setValue(this.horaEntradaStr);
    this.horaSalida.setValue(this.horaSalidaStr);
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;

    let body = {
      "horaEntrada": this.horaEntrada.value.toString(),
      "horaSalida": this.horaSalida.value.toString(),
    };
    if (body.horaEntrada === '') body.horaEntrada = this.horaEntradaStr;
    if (body.horaSalida === '') body.horaSalida = this.horaSalidaStr;

    this.organizacionService
      .patchOrganizacion(this.organizacion, body)
      .subscribe(response => {
        this.organizacion.horaEntrada = body.horaEntrada;
        this.horaEntradaStr = body.horaEntrada;

        this.organizacion.horaSalida = body.horaSalida;
        this.horaSalidaStr = body.horaSalida;

        this.loading = false;
        this.successModal.hide();
      }, error => { console.log(error); this.loading = false });
  }
}
