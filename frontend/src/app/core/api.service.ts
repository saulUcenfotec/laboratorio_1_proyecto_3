import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
export interface Categoria { id:number; nombre:string; descripcion:string }
export interface Producto { id:number; nombre:string; descripcion:string; precio:number; cantidadStock:number; categoriaId:number; categoriaNombre:string }
export interface Usuario { id:number; username:string; roles:string[] }
@Injectable({providedIn:'root'}) export class ApiService {
 private base='http://localhost:8080/api';
 constructor(private http:HttpClient){}
 token(){return localStorage.getItem('token')}
 private headers(){return new HttpHeaders(this.token()?{Authorization:`Bearer ${this.token()}`}:{})}
 login(data:{username:string,password:string}){return this.http.post<{token:string,authorities:string[]}>(`${this.base}/auth/login`,data).pipe(tap(r=>{localStorage.setItem('token',r.token);localStorage.setItem('admin',String(r.authorities.includes('SUPER-ADMIN-ROLE')))}))}
 register(data:{username:string,password:string}){return this.http.post(`${this.base}/usuarios/registro`,data)}
 categorias(){return this.http.get<Categoria[]>(`${this.base}/categorias`,{headers:this.headers()})}
 productos(){return this.http.get<Producto[]>(`${this.base}/productos`,{headers:this.headers()})}
 usuarios(){return this.http.get<Usuario[]>(`${this.base}/usuarios`,{headers:this.headers()})}
 save(path:string,id:number|null,data:unknown){return id?this.http.put(`${this.base}/${path}/${id}`,data,{headers:this.headers()}):this.http.post(`${this.base}/${path}`,data,{headers:this.headers()})}
 delete(path:string,id:number){return this.http.delete(`${this.base}/${path}/${id}`,{headers:this.headers()})}
}
