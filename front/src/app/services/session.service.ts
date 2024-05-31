import { Injectable } from '@angular/core';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.hasToken());

  private hasToken(): boolean {
    return !!localStorage.getItem('authToken');
  }

  public loadSession(): Promise<void> {
    return new Promise((resolve) => {
      const token = localStorage.getItem('authToken');
      if (token) {
        this.isLogged = true;
        this.isLoggedSubject.next(this.isLogged);
        const sessionInformation = localStorage.getItem('sessionInformation');
      if (sessionInformation) {
        this.sessionInformation = JSON.parse(sessionInformation);
      }
      }
      resolve();
    });
  }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    // Stockez le token et la sessionInformation dans le stockage local
    localStorage.setItem('authToken', user.token);
    localStorage.setItem('sessionInformation', JSON.stringify(user));
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    // Supprimez le token et la sessionInformation du stockage local
    localStorage.removeItem('authToken');
    localStorage.removeItem('sessionInformation');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  public getSessionInformation(): SessionInformation {
    return this.sessionInformation || { username: '', email: '', id: 0, token: '', type: ''};
  }
}
