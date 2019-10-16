import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {CheckService} from "../../services/check.service";
import {SmartCheck} from "../../models/smart-check";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";

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

	ngOnInit(): void {
		// generate random values for mainChart
		for (let i = 0; i <= this.mainChartElements; i++) {
			this.mainChartData1.push(this.random(50, 200));
			this.mainChartData2.push(this.random(80, 100));
			this.mainChartData3.push(65);
		}
		this.checkService
				.obtenerChecksDeHoy()
				.subscribe(
						data => {
							console.log("Los checks de hoy se han descargado correctamente");
							console.log(data);

							if (data.length > 0) {
								let checkEntrada = data.filter(r => r.tipoCheck === 'ENTRADA').pop();
								if (checkEntrada !== null) {
									this.horaEntrada = checkEntrada.hora;
									this.tipoCheck = 'SALIDA';
									this.checkDisabled = false;
								}
								else { this.tipoCheck = 'ENTRADA' }

								let checkSalida = data.filter(r => r.tipoCheck === 'SALIDA').pop();
								if (checkSalida !== null) {
									this.horaSalida = checkSalida.hora;
									this.tipoCheck = '';
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
				)
	}

	enviarCheck() {
		this.checkDisabled = true;

		//FIXME la hora del check se debe asignar en el servidor
		let today = new Date();
		let dd = String(today.getDate()).padStart(2, '0');
		let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		let yyyy = today.getFullYear();
		let hh = String(today.getHours()).padStart(2, '0');
		let min = String(today.getMinutes()).padStart(2, '0');
		let ss = String(today.getSeconds()).padStart(2, '0');

		let check = new SmartCheck();
		check.fecha = yyyy + '-' + mm + '-' + dd;
		check.hora = hh + ':' + min + ':' + ss;
		check.empleado = this.authenticationService.currentUserLocation;
		check.tipoCheck = this.tipoCheck;
		this.checkService
				.postCheck(check)
				.subscribe(
						data => {
							console.log("El check ha sido subido correctamente");
							console.log(data);
							this.checkDisabled = false;
							if (this.tipoCheck === 'ENTRADA') {
								this.horaEntrada = check.hora;
								this.tipoCheck = 'SALIDA';
							}
							else if (this.tipoCheck === 'SALIDA') {
								this.horaSalida = check.hora;
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

	public random(min: number, max: number) {
		return Math.floor(Math.random() * (max - min + 1) + min);
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

	// mainChart

	public mainChartElements = 27;
	public mainChartData1: Array<number> = [];
	public mainChartData2: Array<number> = [];
	public mainChartData3: Array<number> = [];
}
