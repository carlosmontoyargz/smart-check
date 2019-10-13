import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";
import {UserService} from "../../services/user.service";
import {AlertService} from "../../services/alert.service";
import {first, map} from "rxjs/operators";

@Component({
	selector: 'app-dashboard',
	templateUrl: 'register.component.html'
})
export class RegisterComponent implements OnInit {
	registerForm: FormGroup;
	loading = false;
	submitted = false;

	constructor(
			private formBuilder: FormBuilder,
			private router: Router,
			private authenticationService: AuthenticationService,
			private userService: UserService,
			private alertService: AlertService
	) {
		// redirect to home if already logged in
		if (this.authenticationService.currentUserValue) {
			this.router.navigate(['/']);
		}
	}

	ngOnInit() {
		this.registerForm = this.formBuilder.group({
			username: ['', [Validators.required, Validators.email]],
			password: ['', [Validators.required, Validators.minLength(6)]],
			confirmPassword: ['', Validators.required]
		});
	}

	// convenience getter for easy access to form fields
	get f() { return this.registerForm.controls; }

	onSubmit() {
		console.log('Entre al submit para guardar el usuario');
		this.submitted = true;

		// reset alerts on submit
		this.alertService.clear();

		// stop here if form is invalid
		if (this.registerForm.invalid) {
			console.log('La forma tiene valores invalidos');
			return;
		}

		this.loading = true;
		this.userService
				.register(this.registerForm.value)
				.subscribe(
						res => {
							console.log('Registro de usuario exitoso!');
							this.router.navigate(['/login']);
						},
						error => {
							console.log('Ocurrio un error al registrar el usuario: ' + error);

							this.alertService.error(error);
							this.loading = false;
						});
	}
}
