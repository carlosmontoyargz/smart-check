import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from "../models/user";
import {environment} from "../../environments/environment";

/**
 * The user service contains a standard set of CRUD methods for managing users,
 * it acts as the interface between the Angular application and the backend api.
 *
 */
@Injectable({ providedIn: 'root' })
export class UserService {
	constructor(private http: HttpClient) { }

	public readonly usersEndpoint = 'usuarios';

	getAll() {
		return this.http.get<User[]>(
				`${environment.apiUrl}/${this.usersEndpoint}`,
				{ params: { "projection": "datos" }});
	}

	get(id: number) {
		return this.http.get<User>(
				`${environment.apiUrl}/${this.usersEndpoint}/${id}`,
				{ params: { "projection": "datos" }})
	}

	register(user: User) {
		console.log('Entre a subir el usuario al web service');
		user.rolNombre = "EMPLEADO";
		return this.http.post(
				`${environment.apiUrl}/${this.usersEndpoint}/registro`,
				user);
	}

	delete(id: number) {
		return this.http.delete(
				`${environment.apiUrl}/${this.usersEndpoint}/${id}`);
	}
}
