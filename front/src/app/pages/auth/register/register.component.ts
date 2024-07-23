// Importation des modules nécessaires depuis Angular et les services locaux
import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterFormService } from '../services/register-form.service';
import { AuthService } from '../services/auth.service';
// Importation de l'interface pour la requête d'inscription
import { RegisterRequest } from 'src/app/interfaces/registerRequest.interface';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

// Déclaration du composant avec son sélecteur, le chemin vers son template HTML et son fichier de style
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {
  // Propriété pour gérer l'affichage des erreurs
  public onError = false;

  // Initialisation du formulaire en utilisant le service RegisterFormService
  public form = this.registerFormService.createForm();

  // Subject pour notifier la destruction du composant
  private destroy$ = new Subject<void>();

  // Injection des dépendances nécessaires dans le constructeur
  constructor(private authService: AuthService,
              private registerFormService: RegisterFormService,
              private router: Router) {
  }

  // Méthode pour gérer la soumission du formulaire
  public submit(): void {
    // Récupération des valeurs du formulaire et casting en tant que RegisterRequest
    const registerRequest = this.form.value as RegisterRequest;
    // Appel du service d'authentification pour enregistrer un nouvel utilisateur
    this.authService.register(registerRequest)
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour désabonner
      .subscribe({
        // En cas de succès, redirection vers la page de connexion
        next: (_: void) => this.router.navigate(['/login']),
        // En cas d'erreur, mise à jour de la propriété onError pour afficher un message d'erreur
        error: _ => this.onError = true,
      });
  }

  // Méthode appelée à la destruction du composant pour désabonner les observables
  ngOnDestroy(): void {
    this.destroy$.next(); // Émet une valeur pour notifier la destruction
    this.destroy$.complete(); // Complète le Subject
  }
}