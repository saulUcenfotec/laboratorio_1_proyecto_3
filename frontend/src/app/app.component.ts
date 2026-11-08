import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ApiService } from './core/api.service';
import { SessionService } from './core/guards';
@Component({selector:'app-root',standalone:true,imports:[CommonModule,RouterOutlet,RouterLink],template:`<header><a class="brand" routerLink="/productos">Mercado Local</a><nav><a routerLink="/productos">Productos</a><a routerLink="/categorias">Categorias</a><a *ngIf="session.admin" routerLink="/usuarios">Usuarios</a></nav><button *ngIf="api.token()" (click)="logout()">Salir</button></header><main><router-outlet/></main>`,styles:[`header{display:flex;align-items:center;gap:32px;padding:18px 6vw;background:#172d3c;color:white}nav{display:flex;gap:22px;flex:1}a{color:inherit;text-decoration:none}.brand{font-size:20px;font-weight:bold}`]})
export class AppComponent {api=inject(ApiService);session=inject(SessionService);private router=inject(Router);logout(){localStorage.clear();this.session.setAdmin(false);this.router.navigateByUrl('/login')}}
