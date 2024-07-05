// Importation des modules Angular et des services nécessaires
import { Component } from '@angular/core'; // Module de base pour créer des composants
import { FormBuilder, Validators } from '@angular/forms'; // Pour la création et la validation de formulaires
import { AuthService } from '../services/auth.service'; // Service d'authentification
import { LoginRequest } from 'src/app/interfaces/loginRequest.interface'; // Interface pour la requête de connexion
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface'; // Interface pour les informations de session
import { Router } from '@angular/router'; // Service pour la navigation
import { SessionService } from 'src/app/services/session.service'; // Service pour gérer la session utilisateur
import { ValidationService } from 'src/app/pages/auth/services/validation.service'; // Service pour les validations personnalisées

// Déclaration du composant avec son sélecteur, son template HTML et son fichier de style
@Component({
  selector: 'app-login', // Le sélecteur CSS pour utiliser ce composant
  templateUrl: './login.component.html', // Le chemin vers le fichier HTML du composant
  styleUrls: ['./login.component.scss'] // Le chemin vers le fichier de style du composant
})
export class LoginComponent {
  public hide = true; // Variable pour contrôler l'affichage du mot de passe
  public onError = false; // Variable pour gérer l'affichage des erreurs de connexion

  // Définition du formulaire avec FormBuilder, incluant les champs email et password avec leurs validations
  public form = this.fb.group({
    email: ['', [Validators.required, this.validationService.emailOrUsernameValidator()]], // Champ email avec validation requise et validation personnalisée
    password: ['', [Validators.required, Validators.minLength(3)]] // Champ mot de passe avec validation requise et longueur minimale
  });

  // Injection des services nécessaires dans le constructeur
  constructor(private authService: AuthService, // Service d'authentification
              private fb: FormBuilder, // FormBuilder pour la création de formulaires
              private router: Router, // Service de navigation
              private sessionService: SessionService, // Service de gestion de session
              private validationService: ValidationService) {} // Service de validation personnalisée

  // Méthode appelée lors de la soumission du formulaire
  public submit(): void {
    const loginRequest = this.form.value as LoginRequest; // Récupération des valeurs du formulaire et cast en LoginRequest
    this.authService.login(loginRequest).subscribe({ // Appel du service d'authentification avec la requête de connexion
      next: (response: SessionInformation) => { // En cas de succès
        this.sessionService.logIn(response); // Mise à jour de la session avec les informations de l'utilisateur
        this.router.navigate(['/me']); // Redirection vers la page de profil de l'utilisateur
      },
      error: error => this.onError = true, // En cas d'erreur, activation de l'affichage d'erreur
    });
  }
}