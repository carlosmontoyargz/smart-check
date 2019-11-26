import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EmpleadosComponent } from './empleados.component';

const routes: Routes = [
  {
    path: '',
    component: EmpleadosComponent,
    data: {
      title: 'Empleados'
    },
    /*children: [
      {
        path: 'estatisticas',
        component: EstadisticasComponent,
        data: {
          title: 'Estadisticas individuales'
        }
      },
    ]*/
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpleadosRoutingModule {}
