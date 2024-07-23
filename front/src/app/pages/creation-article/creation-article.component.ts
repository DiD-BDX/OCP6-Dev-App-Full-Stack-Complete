import { Component, OnInit, OnDestroy } from '@angular/core';
import { Location } from '@angular/common'; // Fournit des méthodes pour naviguer dans l'historique de navigation
import { FormBuilder, Validators } from '@angular/forms'; // Pour la création et validation de formulaires
import { SubscriptionService } from 'src/app/services/subscription.service'; // Service personnalisé pour gérer les abonnements
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface'; // Interface pour les informations d'abonnement
import { SessionService } from 'src/app/services/session.service'; // Service personnalisé pour gérer les sessions utilisateur
import { PostService } from 'src/app/services/post.service'; // Service personnalisé pour gérer les publications
import { Subscription } from 'rxjs'; // Importation de Subscription depuis rxjs

@Component({
  selector: 'app-creation-article', // Le sélecteur CSS pour utiliser ce composant
  templateUrl: './creation-article.component.html', // Le chemin vers le fichier template HTML
  styleUrls: ['./creation-article.component.scss'] // Le chemin vers le fichier de styles CSS
})
export class CreationArticleComponent implements OnInit, OnDestroy {
  public hide = true; // Variable pour contrôler la visibilité d'un élément, par exemple un mot de passe
  public onError = false; // Variable pour indiquer si une erreur est survenue
  public postUsername: string = ''; // Variable pour stocker le nom d'utilisateur associé à un post
  public subscribedTopics: SubscriptionInformation[] = []; // Tableau pour stocker les sujets auxquels l'utilisateur est abonné
  public userId = this.sessionService.getSessionInformation().id; // Récupère l'ID de l'utilisateur depuis le service de session

  // Initialisation du formulaire avec FormBuilder et validation des champs requis
  public form = this.fb.group({
    topic: ['', Validators.required], // Champ pour le sujet, requis
    title: ['', Validators.required], // Champ pour le titre, requis
    content: ['', Validators.required] // Champ pour le contenu, requis
  });

  // Propriété pour stocker les abonnements
  private subscriptions: Subscription = new Subscription();

  // Constructeur pour injecter les services nécessaires
  constructor(
    private fb: FormBuilder, // Injecte FormBuilder pour la gestion du formulaire
    private location: Location, // Injecte Location pour la navigation
    private subscriptionService: SubscriptionService, // Injecte le service d'abonnement
    private sessionService: SessionService, // Injecte le service de session
    private postService: PostService // Injecte le service de publication
  ) {}

  // Méthode ngOnInit appelée après l'initialisation du composant
  ngOnInit(): void {
    this.getSubscribedTopics(); // Appelle la méthode pour récupérer les sujets auxquels l'utilisateur est abonné
  }

  // Méthode pour récupérer les sujets auxquels l'utilisateur est abonné
  getSubscribedTopics(): void {
    // Ajoute l'abonnement à la liste des abonnements
    const subscription = this.subscriptionService.getSubscribedTopics(this.userId).subscribe((subscriptions: SubscriptionInformation[]) => {
      this.subscribedTopics = subscriptions; // Stocke les sujets abonnés dans la variable subscribedTopics
    });
    this.subscriptions.add(subscription);
  }

  // Méthode pour soumettre le formulaire
  submit(): void {
    // Ajoute l'abonnement à la liste des abonnements
    const subscription = this.postService.submitPost(this.form).subscribe(() => {
      this.goBack(); // Navigue vers la page précédente après la soumission
    });
    this.subscriptions.add(subscription);
  }

  // Méthode pour naviguer vers la page précédente
  goBack(): void {
    this.location.back(); // Utilise Location pour naviguer vers l'arrière dans l'historique de navigation
  }

  // Méthode ngOnDestroy appelée avant la destruction du composant
  ngOnDestroy(): void {
    // Désabonne tous les abonnements pour éviter les fuites de mémoire
    this.subscriptions.unsubscribe();
  }
}