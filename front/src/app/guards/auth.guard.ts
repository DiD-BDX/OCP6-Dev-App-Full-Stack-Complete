// Importe les modules nécessaires depuis Angular core et RxJS
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { map, take } from "rxjs/operators";

// Importe les services personnalisés pour la gestion de session et la redirection
import { SessionService } from "../services/session.service";
import { RedirectionService } from "../services/redirection.service";

// Décore la classe avec @Injectable pour permettre son injection dans d'autres classes sans avoir besoin de l'instancier manuellement
@Injectable({ providedIn: 'root' })
export class AuthGuard {
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
            map(isLogged => { // Utilise l'opérateur map pour transformer la valeur émise
                if (!isLogged) { // Si l'utilisateur n'est pas connecté
                    this.redirectionService.redirectToLogin(); // Utilise redirectionService pour rediriger vers la page de connexion
                    return false; // Retourne false, indiquant que la route ne peut pas être activée
                }
                return true; // Si l'utilisateur est connecté, retourne true, permettant l'activation de la route
            })
        );
    }
}