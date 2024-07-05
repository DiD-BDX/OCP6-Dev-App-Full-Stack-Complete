// Importation des modules nécessaires depuis Angular core, forms, router, et RxJS
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

// Importation des interfaces pour la typage fort
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { Topic } from 'src/app/interfaces/topicInformation.interface';

// Importation des services pour la gestion des sessions et des abonnements
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';

// Déclaration du composant avec son sélecteur, template HTML et feuille de style
@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  // Déclaration des variables pour le formulaire, les topics, les topics souscrits et l'ID de l'utilisateur
  profileForm: FormGroup;
  topics: Topic[] = [];
  subscribedTopics: SubscriptionInformation[] = [];
  userId: number;

  // Constructeur du composant, injection des services et initialisation des variables
  constructor(
    private sessionService: SessionService,
    private router: Router,
    private subscriptionService: SubscriptionService
  ) {
    // Récupération de l'ID de l'utilisateur depuis les informations de session
    this.userId = this.sessionService.getSessionInformation().id;
    // Création du formulaire de profil
    this.profileForm = this.createProfileForm();
  }

  // Méthode d'initialisation du composant
  ngOnInit(): void {
    // Initialisation du composant avec la configuration du formulaire et la récupération des données
    this.initializeComponent();
  }

  // Méthode pour vérifier si l'utilisateur est connecté
  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }
  
  // Méthode pour déconnecter l'utilisateur et le rediriger vers la page d'accueil
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  // Méthode pour sauvegarder les informations du formulaire
  save(): void {
    this.updateSessionInformation();
  }

  // Méthode pour se désabonner d'un topic
  unsubscribeFromTopic(topicId: number): void {
    const topic = this.topics.find(topic => topic.id === topicId);
    if (topic) {
      this.unsubscribeUserFromTopic(topic);
    } else {
      console.error('Topic not found');
    }
  }

  // Méthode privée pour créer le formulaire de profil
  private createProfileForm(): FormGroup {
    return new FormGroup({
      username: new FormControl(''),
      email: new FormControl(''),
    });
  }

  // Méthode privée pour initialiser le composant
  private initializeComponent(): void {
    this.setProfileForm();
    this.getTopics();
    this.getSubscribedTopics();
  }

  // Méthode privée pour configurer le formulaire avec les informations de l'utilisateur
  private setProfileForm(): void {
    const sessionInformation = this.sessionService.getSessionInformation();
    if (sessionInformation) {
      this.profileForm.setValue({username: sessionInformation.username, email: sessionInformation.email});
    }
  }

  // Méthode privée pour récupérer les topics disponibles
  private getTopics(): void {
    this.subscriptionService.getTopics().subscribe({
      next: this.handleTopicsResponse.bind(this),
      error: this.handleError.bind(this, 'Erreur lors de la récupération des topics')
    });
  }

  // Méthode privée pour récupérer les topics auxquels l'utilisateur est abonné
  private getSubscribedTopics(): void {
    this.subscriptionService.getSubscribedTopics(this.userId).subscribe({
      next: (subscriptions: SubscriptionInformation[]) => {
        this.subscribedTopics = subscriptions;
      },
      error: this.handleError.bind(this, 'Erreur lors de la récupération des topics souscrits')
    });
  }

  // Méthode privée pour mettre à jour les informations de session de l'utilisateur
  private updateSessionInformation(): void {
    const updatedSessionInformation = {
      ...this.sessionService.getSessionInformation(),
      username: this.profileForm.value.username ?? '',
      email: this.profileForm.value.email ?? ''
    };
    this.sessionService.updateSessionInformation(updatedSessionInformation).subscribe({
      next: this.handleUpdateSessionInformationResponse.bind(this),
      error: this.handleError.bind(this, 'Erreur lors de la mise à jour des informations de session')
    });
  }

  // Méthode privée pour se désabonner d'un topic
  private unsubscribeUserFromTopic(topic: Topic): void {
    this.subscriptionService.unsubscribeUserFromTopic(topic.id, this.userId).subscribe({
      next: this.handleUnsubscribeUserFromTopicResponse.bind(this, topic),
      error: this.handleError.bind(this, 'Erreur lors de la désinscription du topic')
    });
  }

  // Méthode privée pour gérer la réponse après désinscription d'un topic
  private handleUnsubscribeUserFromTopicResponse(topic: Topic, response: { message: string }): void {
    const index = this.subscribedTopics.findIndex(subscription => subscription.topicId === topic.id);
    if (index !== -1) {
      this.subscribedTopics.splice(index, 1);
    }
  }

  // Méthode privée pour gérer la réponse de la récupération des topics
  private handleTopicsResponse(data: Topic[]): void {
    this.topics = data;
  }

  // Méthode privée pour gérer la réponse de la mise à jour des informations de session
  private handleUpdateSessionInformationResponse(updatedSessionInformation: SessionInformation): void {
    console.log('Session information updated', updatedSessionInformation);
  }

  // Méthode privée pour gérer les erreurs
  private handleError(message: string, error: any): void {
    console.error(message, error);
  }
}