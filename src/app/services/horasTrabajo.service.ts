import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {HorasTrabajo, SmartCheck} from "../models/smart-check";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {map} from "rxjs/operators";

/**
 * El servicio de checks contiene un conjunto estandar de metodos CRUD para manejar
 * checks, actua como la interface entre la aplicacion Angular y la API del backend.
 *
 */
@Injectable({ providedIn: 'root' })
export class HorasTrabajoService
{
  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {}

  obtenerHorasActuales() {
    return this.http.get<HorasTrabajo>(
      `${environment.apiUrl}/horasTrabajo/search/findByFechaInicioAndPrincipal`,
      {
        params: {
          "inicio": HorasTrabajoService.parseFirstDayOfCurrentMonth()
        }
      });
  }

  obtenerHorasUsuario(id: string) {
    return this.http.get<HorasTrabajo>(
      `${environment.apiUrl}/horasTrabajo/search/findFirstByFechaInicioAndUsuarioId`,
      {
        params: {
          "inicio": HorasTrabajoService.parseFirstDayOfCurrentMonth(),
          "id": id,
        }
      });
  }

  obtenerHorasActualesId(id: string) {
    console.log(id);
    let url = '';
    if (id === null) {
      url = `${environment.apiUrl}/horasTrabajo/search/findByFechaInicioAndPrincipal`
    }
    else {
      url = `${environment.apiUrl}/horasTrabajo/search/findByFechaInicioAndUsuarioId`
    }
    return this.http.get<HorasTrabajo>(url,
      {
        params: {
          "inicio": HorasTrabajoService.parseFirstDayOfCurrentMonth()
        }
      });
  }

  obtenerTotal(field: string) {
    return this.http.get<number>(
      `${environment.apiUrl}/horasTrabajo/search/findTotal`,
      { params: { field: field } });
  }

  postCheck(check: SmartCheck) {
    return this.http.post(`${environment.apiUrl}/checks`, check);
  }

  obtenerChecksDeHoy() {
    return this.http.get<HorasTrabajo>(
      `${environment.apiUrl}/checks/search/findMyChecksFrom`,
      {
        params: {
          "fecha": this.parseCurrentDate(),
        }
      })
      .pipe(map<any, SmartCheck[]>(r => { return r._embedded.checks }))
  }

  private parseCurrentDate(): string {
    let today = new Date();
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    let yyyy = today.getFullYear();
    return `${yyyy}-${mm}-${dd}`;
  }

  private static parseFirstDayOfCurrentMonth(): string {
    let today = new Date();
    let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    let yyyy = today.getFullYear();
    return `${yyyy}-${mm}-01`;
  }
}
