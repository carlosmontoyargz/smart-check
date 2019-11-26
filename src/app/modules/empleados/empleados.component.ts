import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";

@Component({
  templateUrl: 'empleados.component.html'
})
export class EmpleadosComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private usuarioService: UserService) { }

  usuarios: User[] = [];

  ngOnInit(): void {
    this.route.queryParamMap
      .subscribe(params => {
        console.log(params); // {order: "popular"}
        let page = params.get('page');
        if (page === null) page = "0";
        this.usuarioService.getAll(Number(page))
          .subscribe(users => {
            this.usuarios = users;
          });
      });
  }
}
