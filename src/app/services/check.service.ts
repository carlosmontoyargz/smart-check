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
		let today = new Date();
		let dd = String(today.getDate()).padStart(2, '0');
		let mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
		let yy = today.getFullYear().toString().substr(2, 2);
		return this.http.get<any>(
				`${environment.apiUrl}/checks/search/findByEmpleadoAndFecha`,
				{
					params: {
						"empleado": this.authenticationService.currentUserLocation,
						"fecha": `${dd}/${mm}/${yy}`,
					}
				})
				.pipe(map<any, SmartCheck[]>(r => { return r._embedded.checks }))
	}
}
