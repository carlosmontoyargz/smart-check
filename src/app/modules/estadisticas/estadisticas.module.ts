import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {ChartsModule} from "ng2-charts";
import {BsDropdownModule, ButtonsModule} from "ngx-bootstrap";
import {EstadisticasRoutingModule} from "./estadisticas-routing.module";
import {EstadisticasComponent} from "./estadisticas.component";

@NgModule({
	imports: [
		FormsModule,
		EstadisticasRoutingModule,
		ChartsModule,
		BsDropdownModule,
		ButtonsModule.forRoot()
	],
	declarations: [EstadisticasComponent],
})
export class EstadisticasModule {}
