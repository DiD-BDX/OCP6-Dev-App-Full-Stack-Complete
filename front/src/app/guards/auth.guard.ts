// Importe les modules nécessaires depuis Angular core et RxJS
import { Injectable, OnDestroy } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { map, take, takeUntil } from "rxjs/operators";

// Importe les services personnalisés pour la gestion de session et la redirection
import { SessionService } from "../services/session.service";
import { RedirectionService } from "../services/redirection.service";

// Décore la classe avec @Injectable pour permettre son injection dans d'autres classes sans avoir besoin de l'instancier manuellement
@Injectable({ providedIn: 'root' })
export class AuthGuard implements OnDestroy {
    private destroy$ = new Subject<void>(); // Subject utilisé pour gérer la destruction des observables

    // Déclare le constructeur avec deux services injectés : SessionService et RedirectionService
    constructor(
        private sessionService: SessionService,
        private redirectionService: RedirectionService
    ) {}

    // Définit la méthode canActivate qui retourne un Observable de type boolean
    public canActivate(): Observable<boolean> {
        // Utilise le service sessionService pour vérifier si l'utilisateur est connecté
        return this.sessionService.$isLogged().pipe(
            take(1), // Prend la première valeur émise par le flux et le complète
            takeUntil(this.destroy$), // Utilisation de takeUntil pour désinscrire l'observable
            map(isLogged => { // Utilise l'opérateur map pour transformer la valeur émise
                if (!isLogged) { // Si l'utilisateur n'est pas connecté
                    this.redirectionService.redirectToLogin(); // Utilise redirectionService pour rediriger vers la page de connexion
                    return false; // Retourne false, indiquant que la route ne peut pas être activée
                }
                return true; // Si l'utilisateur est connecté, retourne true, permettant l'activation de la route
            })
        );
    }

    // Méthode appelée lors de la destruction du composant
    ngOnDestroy(): void {
        this.destroy$.next(); // Émission d'un signal pour désinscrire tous les observables
        this.destroy$.complete(); // Complétion du Subject
    }
}