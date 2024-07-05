import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UnauthGuard } from './guards/unauth.guard';
import { AuthGuard } from './guards/auth.guard';
import { MeComponent } from './pages/me/me.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { SessionResolver } from './services/session.resolver';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { CreationArticleComponent } from './pages/creation-article/creation-article.component';
import { ArticlesDetailComponent } from './pages/articles-detail/articles-detail.component';

// Définition des constantes pour les chemins de route
const HOME_PATH = 'home';
const LOGIN_PATH = 'login';
const REGISTER_PATH = 'register';
const ME_PATH = 'me';
const THEMES_PATH = 'themes';
const ARTICLES_PATH = 'articles';
const CREATION_ARTICLE_PATH = 'creation-article';
const ARTICLE_DETAIL_PATH = 'article/:id';
const NOT_FOUND_PATH = '404';
const WILDCARD_PATH = '**';

const routes: Routes = [
  {
    path: '',
    canActivate: [UnauthGuard],
    resolve: { isLogged: SessionResolver },
    component: HomeComponent
  },
  {
    path: HOME_PATH,
    canActivate: [UnauthGuard],
    resolve: { isLogged: SessionResolver },
    component: HomeComponent
  },
  {
    path: LOGIN_PATH,
    canActivate: [UnauthGuard],
    component: LoginComponent
  },
  {
    path: REGISTER_PATH,
    canActivate: [UnauthGuard],
    component: RegisterComponent
  },
  {
    path: ME_PATH,
    canActivate: [AuthGuard],
    resolve: { isLogged: SessionResolver },
    component: MeComponent
  },
  {
    path: THEMES_PATH,
    canActivate: [AuthGuard],
    component: ThemesComponent
  },
  {
    path: ARTICLES_PATH,
    canActivate: [AuthGuard],
    component: ArticlesComponent
  },
  {
    path: CREATION_ARTICLE_PATH,
    canActivate: [AuthGuard],
    component: CreationArticleComponent
  },
  {
    path: ARTICLE_DETAIL_PATH,
    canActivate: [AuthGuard],
    component: ArticlesDetailComponent
  },
  {
    path: NOT_FOUND_PATH,
    component: NotFoundComponent
  },
  {
    path: WILDCARD_PATH,
    redirectTo: NOT_FOUND_PATH
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }