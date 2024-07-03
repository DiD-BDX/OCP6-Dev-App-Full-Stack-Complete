import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class AuthHeaderService {
    constructor() {}

    public getAuthHeaders(): { [header: string]: string | string[] } {
        // Récupérer le jeton JWT du stockage local
        const authToken = localStorage.getItem('authToken');
        if (authToken) {
        // Si le jeton existe, retourner l'en-tête d'autorisation
        return { Authorization: `Bearer ${authToken}` };
        }
        // Si aucun jeton n'est trouvé, retourner un objet vide
        return {};
    }
}