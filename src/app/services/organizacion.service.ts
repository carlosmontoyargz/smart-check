import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Organizacion} from "../models/smart-check";
import {environment} from "../../environments/environment";
import {AuthenticationService} from "./authentication.service";

/**
 * El servicio de checks contiene un conjunto estandar de metodos CRUD para manejar
 * checks, actua como la interface entre la aplicacion Angular y la API del backend.
 *
 */
@Injectable({ providedIn: 'root' })
export class OrganizacionService
{
  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {}

  obtenerOrganizacion(orgNombre: string) {
    return this.http.get<Organizacion>(
      `${environment.apiUrl}/organizaciones/search/findFirstByNombre`,
      { params: { nombre: orgNombre } });
  }
}
