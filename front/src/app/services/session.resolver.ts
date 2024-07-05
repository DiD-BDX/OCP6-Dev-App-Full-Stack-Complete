import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';

@Injectable({
    providedIn: 'root'
})
export class SessionResolver implements Resolve<boolean> {
    // Injection de la dépendance SessionService via le constructeur
    constructor(private sessionService: SessionService) {}

    // Méthode pour résoudre l'état de la session
    resolve(): Observable<boolean> {
        // Retourne un Observable qui indique si l'utilisateur est connecté
        return this.sessionService.$isLogged();
    }
}