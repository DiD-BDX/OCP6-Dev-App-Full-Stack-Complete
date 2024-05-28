import { Component } from '@angular/core';
import { AuthService } from './pages/auth/services/auth.service';
import { NavigationEnd, Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { Observable, filter, map } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isHomePage$: Observable<boolean>;

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService) {
      this.isHomePage$ = this.router.events.pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.router.url === '/' || this.router.url === '/login' || this.router.url === '/register')
      );
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
}
