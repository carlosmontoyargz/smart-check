import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {SmartCheck} from "../models/smart-check";
import {environment} from "../../environments/environment";

/**
 * El servicio de checks contiene un conjunto estandar de metodos CRUD para manejar
 * checks, actua como la interface entre la aplicacion Angular y la API del backend.
 *
 */
@Injectable({ providedIn: 'root' })
export class CheckService {
	constructor(private http: HttpClient) {}

	postCheck(check: SmartCheck) {
		console.log('Entre a subir el check al web service');
		return this.http.post(`${environment.apiUrl}/checks`, check);
	}

	comprobarCheck() {
		console.log('Entre a comprobar el check');
		return this.http.get<SmartCheck>(
				`${environment.apiUrl}/checks/search/findFirstByEmpleadoAndFechaAndTipoCheck`,
				{
					params: {
						"empleado": "/users/1",
						"fecha": "15/10/19",
						"tipoCheck": "ENTRADA"
					}
				});
	}
}
