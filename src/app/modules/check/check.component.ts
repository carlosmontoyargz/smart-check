import { Component, OnInit } from '@angular/core';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {CheckService} from "../../services/check.service";
import {SmartCheck} from "../../models/smart-check";
import {AuthenticationService} from "../../services/authentication.service";
import {User} from "../../models/user";

@Component({
	templateUrl: 'check.component.html'
})
export class CheckComponent implements OnInit {
	constructor(private checkService: CheckService,
				private authenticationService: AuthenticationService) {}

	public tipoCheck = '';
	public checkDisabled = true;
	public horaEntrada = '';
	public horaSalida = '';
	public usuario: User;

	ngOnInit(): void {
		this.usuario = this.authenticationService.currentUserValue;
		this.checkService.obtenerChecksDeHoy().subscribe(
			checks => {
				console.log("Los checks de hoy se han descargado correctamente");
				console.log(checks);

				if (checks.length > 0) {
					let checkEntrada = checks.filter(r => r.tipo === 'ENTRADA').pop();
					if (checkEntrada) {
						this.horaEntrada = new Date(checkEntrada.creado).toTimeString().split(' ')[0];
						this.tipoCheck = 'SALIDA';
						this.checkDisabled = false;
					}
					else { this.tipoCheck = 'ENTRADA' }

					let checkSalida = checks.filter(r => r.tipo === 'SALIDA').pop();
					if (checkSalida) {
						this.horaSalida = new Date(checkSalida.creado).toTimeString().split(' ')[0];
						this.tipoCheck = '';
						this.checkDisabled = true;
					}
					else { this.checkDisabled = false }
				}
				else {
					this.tipoCheck = 'ENTRADA';
					this.checkDisabled = false
				}
			},
			error => {
				console.log("Ocurrio un error al descargar los checks");
				console.log(error);
				this.checkDisabled = false;
			}
		);
	}

	enviarCheck() {
		this.checkDisabled = true;

		let check = new SmartCheck();
		check.tipo = this.tipoCheck;
		console.log("Se ha enviado check...");
		console.log(check);
		this.checkService.postCheck(check).subscribe(
		  checkGuardado => {
		    console.log("El check ha sido subido correctamente");
				console.log(checkGuardado);
				this.checkDisabled = false;
				if (this.tipoCheck === 'ENTRADA') {
					this.horaEntrada = new Date((<SmartCheck> checkGuardado).creado)
            .toTimeString().split(' ')[0];
					this.tipoCheck = 'SALIDA';
				}
				else if (this.tipoCheck === 'SALIDA') {
					this.horaSalida = new Date((<SmartCheck> checkGuardado).creado)
            .toTimeString().split(' ')[0];
					this.tipoCheck = '';
					this.checkDisabled = true;
				}
			},
			error => {
			  console.log("Ocurrio un error al subir el check");
				console.log(error);
				this.checkDisabled = false;
			}
		);
	}

	// barChart1
	public barChart1Data: Array<any> = [
		{
			data: [78, 81, 80, 45, 34, 12, 40, 78, 81, 80, 45, 34, 12, 40, 12, 40],
			label: 'Series A'
		}
	];
	public barChart1Labels: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16'];
	public barChart1Options: any = {
		tooltips: {
			enabled: false,
			custom: CustomTooltips
		},
		maintainAspectRatio: false,
		scales: {
			xAxes: [{
				display: false,
				barPercentage: 0.6,
			}],
			yAxes: [{
				display: false
			}]
		},
		legend: {
			display: false
		}
	};
	public barChart1Colours: Array<any> = [
		{
			backgroundColor: 'rgba(255,255,255,.3)',
			borderWidth: 0
		}
	];
	public barChart1Legend = false;
	public barChart1Type = 'bar';
}
