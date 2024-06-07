import { Topic } from "./topicInformation.interface";


export interface SubscriptionInformation {
    id: number;
    subscribedAt: string;
    topic: Topic;
    topicDescription: string;
    topicName: string;
    topicId: number;
}