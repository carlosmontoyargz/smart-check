import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {CheckService} from "../../services/check.service";
import {DatePipe} from "@angular/common";

@Component({
	templateUrl: 'estadisticas.component.html'
})
export class EstadisticasComponent implements OnInit
{
	constructor(private checkService: CheckService) {}

	public mesActual = new DatePipe('en-US').transform(new Date(), "MMMM y");

	public entradasTemprano = '';
	public retrasos = '';
	public salidasTemprano = '';
	public horasExtra = '';

	public mainChartElements = 31;

	// grafica de entradas
	public mainChartArray1: Array<number> = new Array<number>(this.mainChartElements);
	public mainChartData1: Array<any> = [
		{ label: 'Entrada', data: this.mainChartArray1},
	];
	public mainChartColours1: Array<any> = [
		{ // brandInfo
			backgroundColor: hexToRgba(getStyle('--info'), 10),
			borderColor: getStyle('--success'),
			pointHoverBackgroundColor: '#fff'
		},
		{ // brandSuccess
			backgroundColor: hexToRgba(getStyle('--danger'), 10),
			borderColor: getStyle('--danger'),
			pointHoverBackgroundColor: '#fff'
		},
	];

	// grafica de salida
	public mainChartArray2: Array<number> = new Array<number>(this.mainChartElements);
	public mainChartData2: Array<any> = [
		{ label: 'Entrada', data: this.mainChartArray2},
	];
	public mainChartColours2: Array<any> = [
		{ // brandSuccess
			backgroundColor: hexToRgba(getStyle('--danger'), 10),
			borderColor: getStyle('--danger'),
			pointHoverBackgroundColor: '#fff'
		},
	];

	// formato de graficas
	public mainChartLabels: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', "29", "30", "31"];
	public mainChartOptions: any = {
		tooltips: {
			enabled: false,
			custom: CustomTooltips,
			intersect: true,
			mode: 'index',
			position: 'nearest',
			callbacks: {
				labelColor: function(tooltipItem, chart) {
					return { backgroundColor: chart.data.datasets[tooltipItem.datasetIndex].borderColor };
				}
			}
		},
		responsive: true,
		maintainAspectRatio: false,
		scales: {
			xAxes: [{
				gridLines: {
					drawOnChartArea: false,
				},
				ticks: {
					callback: function(value: any) {
						return value;
					}
				},
			}],
			yAxes: [{
				ticks: {
					callback: function(value: any) {
						return `${value} m`;
					},
					beginAtZero: false,
					maxTicksLimit: 12,
					stepSize: 5,
					min: -30,
					max: 30
				}
			}]
		},
		elements: {
			line: {
				borderWidth: 2
			},
			point: {
				radius: 0,
				hitRadius: 10,
				hoverRadius: 4,
				hoverBorderWidth: 3,
			}
		},
		legend: {
			display: false
		}
	};
	public mainChartLegend = false;
	public mainChartType = 'line';

	public random(min: number, max: number) {
		return Math.floor(Math.random() * (max - min + 1) + min);
	}

	ngOnInit() {
		this.checkService.obtenerChecksDelMes().subscribe(
			checks => {
				console.log("Se han descargado los checks del mes");
				console.log(checks);

				let minTemprano = 0;
				let minTarde = 0;
				let minSalidaTemp = 0;
				let minExtra = 0;

				for (let i = 0; i < this.mainChartElements; i++) {
					let c = checks
						.filter(value =>
								i + 1 === Number(value.fecha.substr(8, 2)) &&
								value.tipo === 'ENTRADA')
						.pop();

					let diff = 0;
					if (c) { diff = c.diferenciaMinutos }
					this.mainChartArray1[i] = diff;

					if (diff > 0) minTarde += diff;
					else minTemprano += diff;
				}
				for (let i = 0; i < this.mainChartElements; i++) {
					let c = checks
							.filter(value =>
									i + 1 === Number(value.fecha.substr(8, 2)) &&
									value.tipo === 'SALIDA')
							.pop();

					let diff = 0;
					if (c) { diff = c.diferenciaMinutos }
					this.mainChartArray2[i] = diff;

					if (diff > 0) minExtra += diff;
					else minSalidaTemp += diff;
				}

				this.entradasTemprano = this.parseMinutes(minTemprano);
				this.retrasos = this.parseMinutes(minTarde);
				this.salidasTemprano = this.parseMinutes(minSalidaTemp);
				this.horasExtra = this.parseMinutes(minExtra);
			},
			error => {
				// TODO mensaje de error
			});
	}

	private parseMinutes(min: number): string {
		if (min < 0) min = -min;
		let horas = Math.trunc(min / 60);
		let minutes = Math.trunc(min % 60);
		return `${String(horas).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`
	}
}
