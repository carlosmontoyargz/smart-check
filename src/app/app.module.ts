import * as $ from 'jquery';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
	CommonModule,
	LocationStrategy,
	HashLocationStrategy
} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {Routes, RouterModule} from '@angular/router';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {FullComponent} from './layouts/full/full.component';

import {NavigationComponent} from './shared/header-navigation/navigation.component';
import {SidebarComponent} from './shared/sidebar/sidebar.component';
import {BreadcrumbComponent} from './shared/breadcrumb/breadcrumb.component';

import {Approutes} from './app-routing.module';
import {AppComponent} from './app.component';
import {SpinnerComponent} from './shared/spinner.component';

// used to create fake backend
//import { fakeBackendProvider } from './_helpers';
import {HomeComponent} from "./component/home/home.component";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {AlertLoginComponent} from "./component/alert-login/alert-login.component";
import {JwtInterceptor} from "./helpers/jwt.interceptor";
import {ErrorInterceptor} from "./helpers/error.interceptor";
import {fakeBackendProvider} from "./helpers/fake-backend";

@NgModule({
	declarations: [
		AppComponent,
		SpinnerComponent,
		FullComponent,
		NavigationComponent,
		BreadcrumbComponent,
		SidebarComponent,
		HomeComponent,
		LoginComponent,
		RegisterComponent,
		AlertLoginComponent
	],
	imports: [
		CommonModule,
		BrowserModule,
		BrowserAnimationsModule,
		FormsModule,
		HttpClientModule,
		NgbModule.forRoot(),
		ReactiveFormsModule,
		RouterModule.forRoot(Approutes, {useHash: false})
	],
	providers: [
		{ provide: LocationStrategy, useClass: HashLocationStrategy, },
		{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
		{ provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

		// provider used to create fake backend
		//fakeBackendProvider
	],
	bootstrap: [AppComponent]
})
export class AppModule {
}
