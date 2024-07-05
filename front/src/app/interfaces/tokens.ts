import { InjectionToken } from '@angular/core'; // Importe InjectionToken depuis le module @angular/core
import { ITopicService } from './topic.service.interface'; // Importe l'interface ITopicService depuis le fichier topic.service.interface
import { ISubscriptionService } from './subscription.service.interface'; // Importe l'interface ISubscriptionService depuis le fichier subscription.service.interface

// Crée un token d'injection pour ITopicService, permettant l'injection de dépendances pour ce service
export const ITopicServiceToken = new InjectionToken<ITopicService>('ITopicService');

// Crée un token d'injection pour ISubscriptionService, permettant l'injection de dépendances pour ce service
export const ISubscriptionServiceToken = new InjectionToken<ISubscriptionService>('ISubscriptionService');