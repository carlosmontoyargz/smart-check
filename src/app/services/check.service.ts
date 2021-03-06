import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {SmartCheck} from "../models/smart-check";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";
import {map} from "rxjs/operators";

/**
 * El servicio de checks contiene un conjunto estandar de metodos CRUD para manejar
 * checks, actua como la interface entre la aplicacion Angular y la API del backend.
 *
 */
@Injectable({ providedIn: 'root' })
export class CheckService
{
	constructor(private http: HttpClient,
				private authenticationService: AuthenticationService) {}

	postCheck(check: SmartCheck) {
		return this.http.post(`${environment.apiUrl}/checks`, check);
	}

	obtenerChecksDeHoy() {
		return this.http.get<any>(
				`${environment.apiUrl}/checks/search/findMyChecksFrom`,
				{
					params: {
						"fecha": this.parseCurrentDate(),
					}
				})
				.pipe(map<any, SmartCheck[]>(r => { return r._embedded.checks }))
	}

	obtenerChecksDe(url: string) {
	  return this.http.get<any>(url)
      .pipe(map<any, SmartCheck[]>(r => { return r._embedded.checks }))
  }

	private parseCurrentDate(): string {
		let today = new Date();
		let dd = String(today.getDate()).padStart(2, '0');
		let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		let yyyy = today.getFullYear();
		return `${yyyy}-${mm}-${dd}`;
	}

	private parseFirstDayOfCurrentMonth(): string {
		let today = new Date();
		let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		let yyyy = today.getFullYear();
		return `${yyyy}-${mm}-01T00:00:00`;
	}
}
