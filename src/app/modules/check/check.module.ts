import {NgModule} from "@angular/core";
import {CheckRoutingModule} from "./check-routing.module";
import {CheckComponent} from "./check.component";
import {FormsModule} from "@angular/forms";
import {ChartsModule} from "ng2-charts";
import {BsDropdownModule, ButtonsModule} from "ngx-bootstrap";
import {CommonModule} from "@angular/common";

@NgModule({
	imports: [
		FormsModule,
		CheckRoutingModule,
		ChartsModule,
		BsDropdownModule,
		ButtonsModule.forRoot(),
		CommonModule
	],
	declarations: [CheckComponent],
})
export class CheckModule {}
