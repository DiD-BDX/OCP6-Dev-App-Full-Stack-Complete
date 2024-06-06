
import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/interfaces/topicInformation.interface';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  topics: Topic[];
  userId: number;

  constructor(
    private subscriptionService: SubscriptionService, 
    private sessionService: SessionService) { 
      this.topics = [];
      this.userId = this.sessionService.getSessionInformation().id;
    }

  ngOnInit(): void {
    this.subscriptionService.getTopics().subscribe({
      next: data => {
        this.topics = data;
      },
      error: error => {
        console.error('Erreur lors de la récupération des topics :', error);
      }
    });
  }

  subscribeToTopic(topic: Topic) {
    this.subscriptionService.subscribeUserToTopic(topic.id, this.userId).subscribe({
      next: response => {
        console.log('Souscription réussie' + response);
        // Gérer la réponse ici, par exemple afficher un message de succès
      },
      error: error => {
        console.error('Erreur lors de la souscription au topic :', error);
        // Gérer l'erreur ici, par exemple afficher un message d'erreur
      }
    });
  }

  /* unsubscribeFromTopic(topic: Topic) {
    this.subscriptionService.unsubscribeUserFromTopic(topic.id, this.userId).subscribe({
      next: response => {
        console.log('Désinscription réussie' + response);
        // Gérer la réponse ici, par exemple afficher un message de succès
      },
      error: error => {
        console.error('Erreur lors de la désinscription du topic :', error);
        // Gérer l'erreur ici, par exemple afficher un message d'erreur
      }
    });
  } */
}