// Importe les dépendances nécessaires pour créer un intercepteur HTTP dans une application Angular.
import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core"; // Importe Injectable pour permettre l'injection de dépendances.
import { AuthHeaderService } from "../services/auth-header.service"; // Importe le service AuthHeaderService pour gérer les en-têtes d'authentification.

// Décore la classe avec @Injectable pour indiquer qu'elle peut être injectée comme dépendance dans d'autres classes.
@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
    // Déclare le constructeur avec une dépendance injectée de type AuthHeaderService.
    constructor(private authHeaderService: AuthHeaderService) {} // Utilisation de l'abstraction

    // Implémente la méthode intercept de l'interface HttpInterceptor.
    public intercept(request: HttpRequest<any>, next: HttpHandler) {
        // Appelle getAuthHeaders sur authHeaderService pour récupérer les en-têtes d'authentification.
        const authHeaders = this.authHeaderService.getAuthHeaders(); // Récupère les headers d'authentification
        // Vérifie si des en-têtes d'authentification existent.
        if (authHeaders) {
            // Clone la requête HTTP entrante et ajoute les en-têtes d'authentification.
            request = request.clone({
                setHeaders: authHeaders,
            });
        }
        // Passe la requête (potentiellement modifiée) au prochain intercepteur dans la chaîne ou au service HTTP si aucun autre intercepteur n'est présent.
        return next.handle(request);
    }
}