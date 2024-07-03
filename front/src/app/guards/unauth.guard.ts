// Importe les dépendances nécessaires depuis Angular et les services locaux
import { Router } from "@angular/router"; // Permet de manipuler la navigation
import { SessionService } from "../services/session.service"; // Service pour gérer la session utilisateur
import { Injectable } from "@angular/core"; // Décorateur pour marquer une classe comme disponible pour être fournie et injectée comme dépendance

// Marque la classe comme injectable avec une portée globale (singleton)
@Injectable({providedIn: 'root'})
export class UnauthGuard  { // Définit une classe de garde (guard) pour les routes non authentifiées

    // Constructeur de la classe, injecte les dépendances nécessaires
    constructor( 
        private router: Router, // Injecte le service Router pour la navigation
        private sessionService: SessionService, // Injecte le service SessionService pour vérifier l'état de connexion
    ) {
    }

    // Méthode pour déterminer si la route peut être activée
    public canActivate(): boolean {
        console.log('UnauthGuard#canActivate called'); // Log pour le débogage
        if (this.sessionService.isLogged) { // Vérifie si l'utilisateur est connecté
            console.log('User is logged in, navigating to /me'); // Log si l'utilisateur est connecté
            this.router.navigate(['me']); // Redirige l'utilisateur vers la route '/me'
            return false; // Empêche l'activation de la route actuelle
        }
        console.log('User is not logged in, access granted'); // Log si l'utilisateur n'est pas connecté
        return true; // Autorise l'activation de la route
    }
}