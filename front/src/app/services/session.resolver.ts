import { Injectable, OnDestroy } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { SessionService } from './session.service';

@Injectable({
    providedIn: 'root'
})
export class SessionResolver implements Resolve<boolean>, OnDestroy {
    private destroy$ = new Subject<void>();

    // Injection de la dépendance SessionService via le constructeur
    constructor(private sessionService: SessionService) {}

    // Méthode pour résoudre l'état de la session
    resolve(): Observable<boolean> {
        // Retourne un Observable qui indique si l'utilisateur est connecté
        // Ajoute takeUntil pour désabonner à la fin de vie du composant
        return this.sessionService.$isLogged().pipe(takeUntil(this.destroy$));
    }

    // Méthode appelée à la fin de vie du composant
    // Complète le Subject pour désabonner tous les observables
    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}