import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService, Usuario } from '../core/api.service';
@Component({standalone:true,imports:[CommonModule],template:`<section><p class="eyebrow">ADMINISTRACION</p><h1>Usuarios registrados</h1><div class="panel table"><table><thead><tr><th>Usuario</th><th>Roles</th></tr></thead><tbody><tr *ngFor="let u of users"><td>{{u.username}}</td><td>{{u.roles.join(', ')}}</td></tr></tbody></table><p class="error">{{error}}</p></div></section>`})
export class UsersComponent {users:Usuario[]=[];error='';constructor(){inject(ApiService).usuarios().subscribe({next:r=>this.users=r,error:()=>this.error='No se pudo cargar la lista de usuarios.'})}}
