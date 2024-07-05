// Importation des modules nécessaires depuis '@angular/core' et '@angular/forms'
import { Injectable } from '@angular/core'; // Permet de décorer la classe pour l'injection de dépendance
import { AbstractControl, ValidationErrors, Validators } from '@angular/forms'; // Importe AbstractControl pour les contrôles de formulaire, ValidationErrors pour les erreurs de validation, et Validators pour les fonctions de validation intégrées

// Décorateur Injectable qui permet à cette classe d'être injectée comme dépendance dans d'autres classes
@Injectable({
    providedIn: 'root' // Spécifie que le service doit être disponible dans l'injecteur racine de l'application
})
export class ValidationService {
    // Constructeur de la classe
    constructor() {}

    // Méthode qui retourne une fonction de validation personnalisée pour vérifier si une valeur est soit un email valide soit un nom d'utilisateur valide
    public emailOrUsernameValidator(): (control: AbstractControl) => ValidationErrors | null {
        // Retourne une fonction qui prend un AbstractControl comme argument et retourne un objet ValidationErrors ou null
        return (control: AbstractControl): ValidationErrors | null => {
            // Récupère la valeur du contrôle
            const value = control.value;
            // Vérifie si la valeur est un email valide en utilisant le validateur intégré 'email' de Angular
            // Si la valeur est un email valide, Validators.email(control) retourne null
            const validEmail = Validators.email(control) === null;
            // Vérifie si la valeur est un nom d'utilisateur valide en s'assurant que la chaîne n'est pas vide et a plus de 3 caractères
            const validUsername = value.trim().length > 3;
            // Si la valeur est un email valide OU un nom d'utilisateur valide, retourne null (pas d'erreur)
            // Sinon, retourne un objet avec une clé 'emailOrUsername' et une valeur true pour indiquer une erreur de validation
            return validEmail || validUsername ? null : { 'emailOrUsername': true };
        };
    }
}