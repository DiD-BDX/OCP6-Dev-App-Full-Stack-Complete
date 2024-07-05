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

  private readonly HOME_PATH = '/';
  private readonly ME_PATH = '/me';

  constructor(
    private location: Location,
    private router: Router,
    private sessionService: SessionService) {
      this.isHomePage$ = this.createHomePageObservable();
      this.subscribeToRouterEvents();
  }

  // Observable pour vérifier si l'utilisateur est sur la page d'accueil
  private createHomePageObservable(): Observable<boolean> {
    return this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.router.url === this.HOME_PATH)
    );
  }

  // S'abonner aux événements du routeur pour mettre à jour l'état de la page "Me"
  private subscribeToRouterEvents(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.isMePage = this.location.path() === this.ME_PATH;
    });
  }

  // Vérifie si l'utilisateur est connecté
  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  // Déconnecte l'utilisateur et le redirige vers la page d'accueil
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([this.HOME_PATH]);
  }

  // Ferme le menu vertical
  public closeVerticalMenu(): void {
    this.verticalMenuOpen = false;
  }
  
  // Bascule l'état du menu vertical
  public toggleVerticalMenu(): void {
    this.verticalMenuOpen = !this.verticalMenuOpen;
  }

  // Retourne à la page précédente
  public goBack(): void {
    this.location.back();
  }
}