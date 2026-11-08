import { Injectable } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
@Injectable({providedIn:'root'}) export class SessionService { admin=localStorage.getItem('admin')==='true'; setAdmin(value:boolean){this.admin=value} }
export const authGuard:CanActivateFn=()=>{const apiToken=localStorage.getItem('token');return apiToken?true:inject(Router).parseUrl('/login')};
export const adminGuard:CanActivateFn=()=>{const session=inject(SessionService);return (session.admin||localStorage.getItem('admin')==='true')?true:inject(Router).parseUrl('/productos')};
