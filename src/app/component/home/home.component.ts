import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";


@Component({ templateUrl: 'home.component.html' })
export class HomeComponent implements OnInit {
	currentUser: User;
	users = [];

	constructor(
			private authenticationService: AuthenticationService,
			private userService: UserService
	) {
		this.currentUser = this.authenticationService.currentUserValue;
	}

	ngOnInit() {
		this.loadAllUsers();
	}

	deleteUser(id: number) {
		this.userService.delete(id)
				.pipe(first())
				.subscribe(() => this.loadAllUsers());
	}

	private loadAllUsers() {
		this.userService.getAll()
				.pipe(first())
				.subscribe(users => this.users = users);
	}
}
