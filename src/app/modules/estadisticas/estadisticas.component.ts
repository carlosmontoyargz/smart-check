import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {CheckService} from "../../services/check.service";

@Component({
	templateUrl: 'estadisticas.component.html'
})
export class EstadisticasComponent implements OnInit
{
	constructor(private checkService: CheckService) {}

	radioModel: string = 'Month';

	// mainChart

	public mainChartElements = 27;
	public mainChartData1: Array<number> = [];
	public mainChartData2: Array<number> = [];

	public mainChartData: Array<any> = [
		{ label: 'Entrada', data: this.mainChartData1},
		{ label: 'Salida', data: this.mainChartData2},
	];
	/* tslint:disable:max-line-length */
	public mainChartLabels: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', "29", "30", "31"];
			//['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Thursday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
	/* tslint:enable:max-line-length */

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
				}
			}],
			yAxes: [{
				ticks: {
					beginAtZero: false,
					maxTicksLimit: 5,
					stepSize: Math.ceil(250 / 5),
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
	public mainChartColours: Array<any> = [
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
	public mainChartLegend = false;
	public mainChartType = 'line';

	// social box charts

	public random(min: number, max: number) {
		return Math.floor(Math.random() * (max - min + 1) + min);
	}

	ngOnInit(): void {

		this.checkService
				.obtenerChecksDelMes()
				.subscribe(
					checks => {
						for (let i = 0; i <=this.mainChartElements; i++) {
							let c = checks
								.filter(value =>
										i === new Date(value.fecha).getDate() &&
										value.tipoCheck === 'ENTRADA')
								.pop();

							if (c) {
								this.mainChartData1.push(c.diferenciaMinutos)
							} else {
								this.mainChartData1.push(0)
							}
						}
					},
					error => {

					});

		// generate random values for mainChart
		for (let i = 0; i <= this.mainChartElements; i++) {
			this.mainChartData2.push(this.random(-20, 10));
		}
	}
}
