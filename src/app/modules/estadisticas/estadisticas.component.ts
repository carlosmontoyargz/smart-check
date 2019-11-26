import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import {CheckService} from "../../services/check.service";
import {DatePipe} from "@angular/common";
import {HorasTrabajoService} from "../../services/horasTrabajo.service";
import { ActivatedRoute } from '@angular/router';
import {UserService} from "../../services/user.service";

@Component({
	templateUrl: 'estadisticas.component.html'
})
export class EstadisticasComponent implements OnInit
{
	constructor(private checkService: CheckService,
              private horasService: HorasTrabajoService,
              private usuarioService: UserService,
              private route: ActivatedRoute) {}

  mesActual = new DatePipe('en-US').transform(new Date(), "MMMM y");
  entradasTemprano: string;
  retrasos: string;
  salidasTemprano: string;
  horasExtra: string;

	ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      console.log(params);
      Promise.resolve(1)
        .then(value => {
          console.log(value);
          let id = params.get('id');
          if (id === null)
            return this.horasService.obtenerHorasActuales().toPromise();
          else
            return this.horasService.obtenerHorasUsuario(id).toPromise();
        })
        .then(horas => {
          console.log("Se han descargado las horas del mes");
          console.log(horas);
          this.entradasTemprano = EstadisticasComponent.parseMinutes(horas.entradasTemprano);
          this.retrasos = EstadisticasComponent.parseMinutes(horas.retrasos);
          this.salidasTemprano = EstadisticasComponent.parseMinutes(horas.salidasTemprano);
          this.horasExtra = EstadisticasComponent.parseMinutes(horas.extras);
          return this.checkService
            .obtenerChecksDe(horas._links.checks.href)
            .toPromise();
        })
        .then(checks => {
          console.log("Se han descargado los checks del periodo");
          console.log(checks);
          let datosEntradas = new Array<number>(this.mainChartElements).fill(0);
          for (let i = 0; i < this.mainChartElements; i++) {
            let entrada = checks
              .filter(v => v.tipo === 'ENTRADA' && i + 1 === new Date(v.creado).getDate())
              .pop();
            if (entrada) { datosEntradas[i] = entrada.diferencia }
          }
          this.datasetEntradas[0].data = datosEntradas;
          let datosSalidas = new Array<number>(this.mainChartElements).fill(0);
          for (let i = 0; i < this.mainChartElements; i++) {
            let salida = checks
              .filter(v => v.tipo === 'SALIDA' && i + 1 === new Date(v.creado).getDate())
              .pop();
            if (salida) { datosSalidas[i] = salida.diferencia }
          }
          this.datasetSalidas[0].data = datosSalidas;
          console.log(this.datasetEntradas[0].data);
          console.log(this.datasetSalidas[0].data);
          return 1;
        })
        .catch(e => {
          console.info(e);
          this.entradasTemprano = "00:00";
          this.retrasos = "00:00";
          this.salidasTemprano = "00:00";
          this.horasExtra = "00:00";
          this.datasetEntradas[0].data = new Array<number>(this.mainChartElements).fill(0);
          this.datasetSalidas[0].data = new Array<number>(this.mainChartElements).fill(0);
        });
    });
	}

  private static parseMinutes(min: number): string {
    if (min < 0) min = -min;
    let horas = Math.trunc(min / 60);
    let minutes = Math.trunc(min % 60);
    return `${String(horas).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`
  }

  public mainChartElements = 31;

  // grafica de entradas
  public datasetEntradas: Array<any> = [
    { label: 'Entrada', data: new Array<number>(this.mainChartElements)},
  ];
  public coloresEntradas: Array<any> = [
    { // brandInfo
      backgroundColor: hexToRgba(getStyle('--info'), 10),
      borderColor: getStyle('--success'),
      pointHoverBackgroundColor: '#fff'
    },
  ];

  // grafica de salida
  public datasetSalidas: Array<any> = [
    { label: 'Salidas', data: new Array<number>(this.mainChartElements)},
  ];
  public coloresSalidas: Array<any> = [
    { // brandSuccess
      backgroundColor: hexToRgba(getStyle('--danger'), 10),
      borderColor: getStyle('--danger'),
      pointHoverBackgroundColor: '#fff'
    },
  ];

  // formato de graficas
  public labelsDias: Array<any> = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', "29", "30", "31"];
  public opcionesGraficas: any = {
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
}
