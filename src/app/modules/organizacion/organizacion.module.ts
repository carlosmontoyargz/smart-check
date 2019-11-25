// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import {OrganizacionComponent} from "./organizacion.component";
import {OrganizacionRoutingModule} from "./organizacion.routing.module";

// Modal Component
import { ModalModule } from 'ngx-bootstrap/modal';

@NgModule({
	imports: [
		CommonModule,
		OrganizacionRoutingModule,
		BsDropdownModule.forRoot(),
    ModalModule.forRoot()
	],
	declarations: [
		OrganizacionComponent
	]
})
export class OrganizacionModule { }
