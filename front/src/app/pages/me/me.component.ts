import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { Topic } from 'src/app/interfaces/topicInformation.interface';

import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {
  profileForm: FormGroup; // Formulaire pour les informations de profil
  topics: Topic[] = []; // Liste des topics disponibles
  subscribedTopics: SubscriptionInformation[] = []; // Liste des topics souscrits par l'utilisateur
  userId: number; // ID de l'utilisateur
  private destroy$ = new Subject<void>(); // Subject utilisé pour gérer la destruction des observables

  constructor(
    private sessionService: SessionService,
    private router: Router,
    private subscriptionService: SubscriptionService
  ) {
    this.userId = this.sessionService.getSessionInformation().id; // Récupération de l'ID de l'utilisateur depuis le service de session
    this.profileForm = this.createProfileForm(); // Création du formulaire de profil
  }

  ngOnInit(): void {
    this.initializeComponent(); // Initialisation du composant
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged(); // Observable pour vérifier si l'utilisateur est connecté
  }
  
  public logout(): void {
    this.sessionService.logOut(); // Déconnexion de l'utilisateur
    this.router.navigate(['']); // Redirection vers la page d'accueil
  }

  save(): void {
    this.updateSessionInformation(); // Sauvegarde des informations de session
  }

  unsubscribeFromTopic(topicId: number): void {
    const topic = this.topics.find(topic => topic.id === topicId); // Recherche du topic par ID
    if (topic) {
      this.unsubscribeUserFromTopic(topic); // Désinscription de l'utilisateur du topic
    } else {
      console.error('Topic not found'); // Affichage d'une erreur si le topic n'est pas trouvé
    }
  }

  private createProfileForm(): FormGroup {
    return new FormGroup({
      username: new FormControl(''), // Champ pour le nom d'utilisateur
      email: new FormControl(''), // Champ pour l'email
    });
  }

  private initializeComponent(): void {
    this.setProfileForm(); // Initialisation du formulaire de profil
    this.getTopics(); // Récupération des topics disponibles
    this.getSubscribedTopics(); // Récupération des topics souscrits par l'utilisateur
  }

  private setProfileForm(): void {
    const sessionInformation = this.sessionService.getSessionInformation(); // Récupération des informations de session
    if (sessionInformation) {
      this.profileForm.setValue({username: sessionInformation.username, email: sessionInformation.email}); // Mise à jour du formulaire avec les informations de session
    }
  }

  private getTopics(): void {
    this.subscriptionService.getTopics()
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour gérer la désinscription
      .subscribe({
        next: this.handleTopicsResponse.bind(this), // Gestion de la réponse en cas de succès
        error: this.handleError.bind(this, 'Erreur lors de la récupération des topics') // Gestion de l'erreur
      });
  }

  private getSubscribedTopics(): void {
    this.subscriptionService.getSubscribedTopics(this.userId)
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour gérer la désinscription
      .subscribe({
        next: (subscriptions: SubscriptionInformation[]) => {
          this.subscribedTopics = subscriptions; // Mise à jour de la liste des topics souscrits
        },
        error: this.handleError.bind(this, 'Erreur lors de la récupération des topics souscrits') // Gestion de l'erreur
      });
  }

  private updateSessionInformation(): void {
    const updatedSessionInformation = {
      ...this.sessionService.getSessionInformation(),
      username: this.profileForm.value.username ?? '',
      email: this.profileForm.value.email ?? ''
    };
    this.sessionService.updateSessionInformation(updatedSessionInformation)
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour gérer la désinscription
      .subscribe({
        next: this.handleUpdateSessionInformationResponse.bind(this), // Gestion de la réponse en cas de succès
        error: this.handleError.bind(this, 'Erreur lors de la mise à jour des informations de session') // Gestion de l'erreur
      });
  }

  private unsubscribeUserFromTopic(topic: Topic): void {
    this.subscriptionService.unsubscribeUserFromTopic(topic.id, this.userId)
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour gérer la désinscription
      .subscribe({
        next: this.handleUnsubscribeUserFromTopicResponse.bind(this, topic), // Gestion de la réponse en cas de succès
        error: this.handleError.bind(this, 'Erreur lors de la désinscription du topic') // Gestion de l'erreur
      });
  }

  private handleUnsubscribeUserFromTopicResponse(topic: Topic, response: { message: string }): void {
    const index = this.subscribedTopics.findIndex(subscription => subscription.topicId === topic.id);
    if (index !== -1) {
      this.subscribedTopics.splice(index, 1); // Suppression du topic de la liste des topics souscrits
    }
  }

  private handleTopicsResponse(data: Topic[]): void {
    this.topics = data; // Mise à jour de la liste des topics disponibles
  }

  private handleUpdateSessionInformationResponse(updatedSessionInformation: SessionInformation): void {
    console.log('Session information updated', updatedSessionInformation); // Affichage d'un message de succès
  }

  private handleError(message: string, error: any): void {
    console.error(message, error); // Affichage d'un message d'erreur
  }

  ngOnDestroy(): void {
    this.destroy$.next(); // Émission d'une valeur pour notifier la destruction
    this.destroy$.complete(); // Fermeture du Subject
  }
}