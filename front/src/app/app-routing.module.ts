import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { MeComponent } from './pages/me/me.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { SessionResolver } from './services/session.resolver';
import { AppComponent } from './app.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ThemesComponent } from './pages/themes/themes.component';

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    //loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
    resolve: { isLogged: SessionResolver },
    component: HomeComponent
  },
  {
    path: 'home',
    canActivate: [UnauthGuard],
    resolve: { isLogged: SessionResolver },
    component: HomeComponent
  },
{
    path: 'login',
    canActivate: [UnauthGuard],
    component: LoginComponent
},
{
    path: 'register',
    canActivate: [UnauthGuard],
    component: RegisterComponent
},
  {
    path: 'me',
    canActivate: [AuthGuard],
    resolve: { isLogged: SessionResolver },
    component: MeComponent
  },
{
    path: 'themes',
    canActivate: [AuthGuard],
    component: ThemesComponent
},
{ path: '404', component: NotFoundComponent },
{ path: '**', redirectTo: '404' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}