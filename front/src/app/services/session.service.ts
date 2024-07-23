import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject, Observable, Subject, tap } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService implements OnDestroy {
  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.hasToken());
  private destroy$ = new Subject<void>();

  // Clés de stockage local
  private readonly AUTH_TOKEN_KEY = 'authToken';
  private readonly SESSION_INFO_KEY = 'sessionInformation';

  constructor(private http: HttpClient, private httpService: HttpService) { }

  // Vérifie si un token est présent dans le stockage local
  private hasToken(): boolean {
    return !!localStorage.getItem(this.AUTH_TOKEN_KEY);
  }

  // Charge les informations de session depuis le stockage local
  public loadSession(): Promise<void> {
    return new Promise((resolve) => {
      const token = localStorage.getItem(this.AUTH_TOKEN_KEY);
      if (token) {
        this.isLogged = true;
        this.isLoggedSubject.next(this.isLogged);
        const sessionInformation = localStorage.getItem(this.SESSION_INFO_KEY);
        if (sessionInformation) {
          this.sessionInformation = JSON.parse(sessionInformation);
        }
      }
      resolve();
    });
  }

  // Retourne un Observable indiquant si l'utilisateur est connecté
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable().pipe(takeUntil(this.destroy$));
  }

  // Connecte l'utilisateur et met à jour le stockage local
  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    localStorage.setItem(this.AUTH_TOKEN_KEY, user.token);
    localStorage.setItem(this.SESSION_INFO_KEY, JSON.stringify(user));
    this.next();
  }

  // Déconnecte l'utilisateur et met à jour le stockage local
  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    localStorage.removeItem(this.AUTH_TOKEN_KEY);
    localStorage.removeItem(this.SESSION_INFO_KEY);
    this.next();
  }

  // Met à jour l'état de connexion
  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  // Retourne les informations de session
  public getSessionInformation(): SessionInformation {
    return this.sessionInformation || { username: '', email: '', id: 0, token: '', type: ''};
  }

  // Met à jour les informations de session via une requête HTTP
  public updateSessionInformation(sessionInformation: SessionInformation): Observable<SessionInformation> {
    return this.httpService.put<SessionInformation>('/api/user/update', sessionInformation).pipe(
      tap(updatedSessionInformation => {
        this.sessionInformation = updatedSessionInformation;
        localStorage.setItem(this.SESSION_INFO_KEY, JSON.stringify(updatedSessionInformation));
      }),
      takeUntil(this.destroy$)
    );
  }

  // Méthode appelée à la fin de vie du composant
  // Complète le Subject pour désabonner tous les observables
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}