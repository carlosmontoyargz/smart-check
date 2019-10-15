import {NgModule} from "@angular/core";
import {CheckRoutingModule} from "./check-routing.module";
import {CheckComponent} from "./check.component";
import {FormsModule} from "@angular/forms";
import {ChartsModule} from "ng2-charts";
import {BsDropdownModule, ButtonsModule} from "ngx-bootstrap";

@NgModule({
	imports: [
		FormsModule,
		CheckRoutingModule,
		ChartsModule,
		BsDropdownModule,
		ButtonsModule.forRoot()
	],
	declarations: [CheckComponent],
})
export class CheckModule {}
