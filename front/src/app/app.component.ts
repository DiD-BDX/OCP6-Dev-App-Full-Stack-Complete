import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { Observable, filter, map } from 'rxjs';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  isHomePage$: Observable<boolean>;
  isMePage: boolean = false;
  verticalMenuOpen = false;

  constructor(
    private location: Location,
    private router: Router,
    private sessionService: SessionService) {
      this.isHomePage$ = this.router.events.pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => this.router.url === '/')
      );
      this.router.events.pipe(
        filter(event => event instanceof NavigationEnd)
      ).subscribe(() => {
        this.isMePage = this.location.path() === '/me';
      });
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  closeVerticalMenu() {
    this.verticalMenuOpen = false;
  }
  
  toggleVerticalMenu() {
    this.verticalMenuOpen = !this.verticalMenuOpen;
  }

  goBack(): void {
    this.location.back();
  }
}
