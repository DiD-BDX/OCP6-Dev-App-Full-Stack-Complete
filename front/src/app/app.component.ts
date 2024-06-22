import { Component } from '@angular/core';
import { AuthService } from './pages/auth/services/auth.service';
import { NavigationEnd, Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { Observable, filter, map, startWith } from 'rxjs';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isHomePage$: Observable<boolean>;
  verticalMenuOpen = false;

  constructor(
    private authService: AuthService,
    private location: Location,
    private router: Router,
    private sessionService: SessionService) {
      this.isHomePage$ = this.router.events.pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.router.url === '/')
      );
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  toggleVerticalMenu() {
    this.verticalMenuOpen = !this.verticalMenuOpen;
  }
  
  goBack(): void {
    this.location.back();
  }
}
