import { Injectable } from '@angular/core';

// Fonction pour récupérer le jeton d'authentification depuis le stockage local
// Retourne le jeton sous forme de chaîne de caractères ou null si aucun jeton n'est trouvé
function getAuthToken(): string | null {
    return localStorage.getItem('authToken');
}

@Injectable({
    providedIn: 'root'
})
export class AuthHeaderService {
    constructor() {}

    // Méthode pour obtenir les en-têtes d'autorisation
    // Retourne un objet contenant l'en-tête d'autorisation si le jeton est présent
    // Sinon, retourne un objet vide
    public getAuthHeaders(): { [header: string]: string | string[] } {
        // Utilise la fonction pour récupérer le jeton d'authentification
        const authToken = getAuthToken();
        if (authToken) {
            // Si le jeton existe, retourner l'en-tête d'autorisation
            return { Authorization: `Bearer ${authToken}` };
        }
        // Si aucun jeton n'est trouvé, retourner un objet vide
        return {};
    }
}