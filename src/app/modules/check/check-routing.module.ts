import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CheckComponent} from "./check.component";

const routes: Routes = [
	{
		path: '',
		component: CheckComponent,
		data: {
			title: 'Check'
		},
	}
];

@NgModule({
	imports: [RouterModule.forChild(routes)],
	exports: [RouterModule]
})
export class CheckRoutingModule {}
