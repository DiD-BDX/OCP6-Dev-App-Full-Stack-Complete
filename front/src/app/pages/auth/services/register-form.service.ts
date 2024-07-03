// Importation des modules nécessaires depuis le framework Angular
import { Injectable } from '@angular/core'; // Permet de décorer la classe pour l'injection de dépendance
import { FormBuilder, Validators } from '@angular/forms'; // Importe FormBuilder pour la création de formulaires réactifs et Validators pour la validation des champs

// Décorateur Injectable qui permet à cette classe d'être injectée comme dépendance dans d'autres classes
@Injectable({
    providedIn: 'root' // Spécifie que le service doit être disponible dans l'injecteur racine de l'application
})
export class RegisterFormService {
    // Injection du service FormBuilder dans le constructeur pour pouvoir l'utiliser pour construire le formulaire
    constructor(private fb: FormBuilder) {}

    // Méthode publique pour créer et retourner un groupe de contrôles de formulaire (un formulaire réactif)
    public createForm() {
        // Utilisation de FormBuilder pour créer un groupe de contrôles de formulaire
        return this.fb.group({
            // Définition du contrôle 'email' avec une valeur initiale vide et des validateurs pour exiger une saisie et valider le format de l'email
            email: ['', [Validators.required, Validators.email]],
            // Définition du contrôle 'username' avec une valeur initiale vide, un validateur pour exiger une saisie, et des validateurs pour la longueur minimale et maximale du nom d'utilisateur
            username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
            // Définition du contrôle 'password' avec une valeur initiale vide, des validateurs pour exiger une saisie, une longueur minimale et maximale, et un pattern pour exiger un format spécifique (au moins une lettre majuscule, une lettre minuscule, un chiffre, et un caractère spécial)
            password: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(40), Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,40}$')]]
        });
    }
}