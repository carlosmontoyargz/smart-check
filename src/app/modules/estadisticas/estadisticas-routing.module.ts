import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {EstadisticasComponent} from "./estadisticas.component";

// import { CardsComponent } from './cards.component';

const routes: Routes = [
	{
		path: '',
		component: EstadisticasComponent,
		data: {
			title: 'Check'
		},
		/*children: [
			{
				path: '',
				redirectTo: 'cards'
			},
			{
				path: 'cards',
				component: CardsComponent,
				data: {
					title: 'Cards'
				}
			},
		]*/
	}
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class EstadisticasRoutingModule {}
