import { Observable } from "rxjs"; // Importe la classe Observable depuis le module rxjs
import { Topic } from "./topicInformation.interface"; // Importe l'interface Topic depuis le fichier topicInformation.interface

// Déclare une interface ITopicService qui définit un contrat pour les services de gestion des sujets
export interface ITopicService {
    // Déclare une méthode getTopics qui retourne un Observable d'un tableau de Topic
    getTopics(): Observable<Topic[]>;
}