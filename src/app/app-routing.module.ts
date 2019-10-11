import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {FullComponent} from './layouts/full/full.component';
import {HomeComponent} from "./component/home/home.component";
import {AuthGuard} from "./helpers/auth.guard";
import {RegisterComponent} from "./component/register/register.component";
import {LoginComponent} from "./component/login/login.component";

export const Approutes: Routes = [
	/*{
		path: '',
		component: FullComponent,
		children: [
			{path: '', redirectTo: '/starter', pathMatch: 'full'},
			{
				path: 'starter',
				loadChildren: () => import('./starter/starter.module').then(m => m.StarterModule)
			},
			{
				path: 'component',
				loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule)
			}
		]
	},*/
	{ path: '', component: HomeComponent, canActivate: [AuthGuard] },
	{ path: 'login', component: LoginComponent } ,
	{ path: 'register', component: RegisterComponent },

	// otherwise redirect to home
	{ path: '**', redirectTo: '' }
	/*{
	  path: '**',
	  redirectTo: '/starter'
	}*/
];
