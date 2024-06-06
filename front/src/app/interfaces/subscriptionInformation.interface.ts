import { Topic } from "./topicInformation.interface";


export interface SubscriptionInformation {
    id: number;
    subscribedAt: string;
    topic: Topic;
}