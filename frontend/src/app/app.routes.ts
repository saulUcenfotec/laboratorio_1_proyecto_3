import { Routes } from '@angular/router';
import { LoginComponent, RegisterComponent } from './pages/auth.component';
import { CatalogComponent } from './pages/catalog.component';
import { UsersComponent } from './pages/users.component';
import { authGuard, adminGuard } from './core/guards';
export const routes:Routes=[{path:'login',component:LoginComponent},{path:'registro',component:RegisterComponent},{path:'productos',component:CatalogComponent,canActivate:[authGuard],data:{kind:'productos'}},{path:'categorias',component:CatalogComponent,canActivate:[authGuard],data:{kind:'categorias'}},{path:'usuarios',component:UsersComponent,canActivate:[authGuard,adminGuard]},{path:'',pathMatch:'full',redirectTo:'productos'},{path:'**',redirectTo:'productos'}];
