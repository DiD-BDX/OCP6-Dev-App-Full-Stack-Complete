import { Injectable } from '@angular/core';
import { SessionInformation } from '../interfaces/sessionInformation.interface';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    this.isLogged = true;
    // Stockez le token dans le stockage local
    localStorage.setItem('authToken', user.token);
    this.next();
  }

  public logOut(): void {
    this.sessionInformation = undefined;
    this.isLogged = false;
    // Supprimez le token du stockage local
    localStorage.removeItem('authToken');
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}
