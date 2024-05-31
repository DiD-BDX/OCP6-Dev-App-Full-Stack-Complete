import { APP_INITIALIZER, NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserModule } from '@angular/platform-browser';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { MeComponent } from './pages/me/me.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { MatCardHeader, MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SessionService } from './services/session.service';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';

const materialModule = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatSnackBarModule,
  MatToolbarModule,
]

export function initializeApp(sessionService: SessionService) {
  return (): Promise<any> => { 
    return sessionService.loadSession();
  }
}

@NgModule({
  declarations: [AppComponent, HomeComponent, MeComponent, NotFoundComponent, LoginComponent, RegisterComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    ...materialModule
  ],
  providers: [
    SessionService,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [SessionService],
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
