import { Observable } from "rxjs"; // Importation de la classe Observable depuis RxJS
import { SubscriptionActionResult } from "./subscriptionActionResult.interface";

// Interface ISubscriptionService définissant un contrat pour les services de souscription
export interface ISubscriptionService {
    /**
     * Souscrit un utilisateur à un topic.
     * @param topicId - ID du topic.
     * @param userId - ID de l'utilisateur.
     * @returns Un Observable émettant la réponse de la souscription.
     */
    subscribeUserToTopic(topicId: number, userId: number): Observable<SubscriptionActionResult>;
}