import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OrganizacionComponent} from "./organizacion.component";

// import { CardsComponent } from './cards.component';

const routes: Routes = [
	{
		path: '',
		component: OrganizacionComponent,
		data: {
			title: 'Organizacion'
		}
	}
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class OrganizacionRoutingModule {}
