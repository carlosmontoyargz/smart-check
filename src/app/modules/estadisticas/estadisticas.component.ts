import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';

@Component({
	templateUrl: 'estadisticas.component.html'
})
export class EstadisticasComponent implements OnInit {

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
	public mainChartLabels: Array<any> = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday', 'Monday', 'Thursday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
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
						return value.charAt(0);
					}
				}
			}],
			yAxes: [{
				ticks: {
					beginAtZero: true,
					maxTicksLimit: 5,
					stepSize: Math.ceil(250 / 5),
					max: 250
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
		// generate random values for mainChart
		for (let i = 0; i <= this.mainChartElements; i++) {
			this.mainChartData1.push(this.random(50, 200));
			this.mainChartData2.push(this.random(80, 100));
		}
	}
}
