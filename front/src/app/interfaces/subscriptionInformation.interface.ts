//Cette interface est conçue pour typer les objets qui contiennent des informations sur une souscription, 
//incluant l'identifiant de la souscription, la date de souscription, les détails du sujet abonné 
//(y compris un objet Topic complet, la description du sujet, son nom, et son identifiant).

import { Topic } from "./topicInformation.interface";


export interface SubscriptionInformation {
    id: number;
    subscribedAt: string;
    topic: Topic;
    topicDescription: string;
    topicName: string;
    topicId: number;
}