// Angular
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {ChartsModule} from "ng2-charts";
import { NgModule } from '@angular/core';


// Tabs Component
import { TabsModule } from 'ngx-bootstrap/tabs';

// Dropdowns Component
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

// Components Routing
import { EmpleadosRoutingModule } from './empleados-routing.module';
import {EmpleadosComponent} from "./empleados.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ChartsModule,
    EmpleadosRoutingModule,
    BsDropdownModule.forRoot(),
    TabsModule,
  ],
  declarations: [
    EmpleadosComponent,
  ]
})
export class EmpleadosModule { }
