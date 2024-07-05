import { Injectable } from "@angular/core";
import { Router } from "@angular/router";

@Injectable({ providedIn: 'root' })
export class RedirectionService {
    // Injection de la dépendance Router via le constructeur
    constructor(private router: Router) {}

    // Méthode pour rediriger vers la page de login
    redirectToLogin() {
        // Utilisation d'une constante pour la route de redirection
        const loginRoute = 'login';
        this.router.navigate([loginRoute]);
    }
}