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

	public tipoCheck: string = 'ENTRADA';

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


	public random(min: number, max: number) {
		return Math.floor(Math.random() * (max - min + 1) + min);
	}

	ngOnInit(): void {
		// generate random values for mainChart
		for (let i = 0; i <= this.mainChartElements; i++) {
			this.mainChartData1.push(this.random(50, 200));
			this.mainChartData2.push(this.random(80, 100));
			this.mainChartData3.push(65);
		}

		this.checkService
				.comprobarCheck()
				.subscribe(
						data => {
							console.log("El check ha comprobado correctamente");
							console.log(data)
						},
						error => {
							console.log("Ocurrio un error al comprobar el check");
							console.log(error)
						}
				)
	}

	enviarCheck() {
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
		check.empleado = `/${UserService.usersEndpoint}/${this.authenticationService.currentUserValue.id}`;
		check.tipoCheck = this.tipoCheck;
		console.log("Check a enviar al servicio web: " + JSON.stringify(check));

		this.checkService
				.postCheck(check)
				.subscribe(
						data => {
							console.log("El check ha sido subido correctamente");
							console.log(data)
						},
						error => {
							console.log("Ocurrio un error al subir el check");
							console.log(error)
						}
				);
	}
}
