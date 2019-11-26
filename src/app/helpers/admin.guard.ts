import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { AuthenticationService } from '../services/authentication.service';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authenticationService.currentUserValue;
    if (currentUser) {
      // Los empleados no tienen permiso de ver la pagina
      console.log('Entrando a evaluacion de rol');
      if (currentUser.rolNombre === 'EMPLEADO') {
        console.log('Empleado no puede acceder, redireccionando a 404...');
        this.router.navigate(['/404']);
        return false
      }
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.router.navigate(
      ['/login'],
      { queryParams: { returnUrl: state.url }});
    return false;
  }
}
