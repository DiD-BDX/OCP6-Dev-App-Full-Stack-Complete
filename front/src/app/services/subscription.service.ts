import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Topic } from '../interfaces/topicInformation.interface';
import { SubscriptionInformation } from '../interfaces/subscriptionInformation.interface';

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    constructor(private http: HttpClient) { }
    

    subscribeUserToTopic(topicId: number, userId: number): Observable<any> {
        return this.http.post(`/api/topics/${topicId}/subscribe`, { userId: userId }).pipe(
            tap(response => {
            }),
            catchError(error => {
                if (error.status === 409) { // Suppose que votre API renvoie une erreur 409 si l'utilisateur est déjà abonné
                    return of('User is already subscribed to this topic');
                } else {
                    throw error;
                }
            })
        );
    }

    unsubscribeUserFromTopic(topicId: number, userId: number): Observable<any> {
        const params = new HttpParams().set('userId', userId.toString());
        return this.http.delete(`/api/topics/${topicId}/unsubscribe`, { params }).pipe(
            tap(response => {
            }),
            catchError(error => {
                throw error;
            })
        );
    }

    getSubscribedTopics(userId: number): Observable<SubscriptionInformation[]> {
        return this.http.get<SubscriptionInformation[]>(`/api/topics/${userId}/subscriptions`);
    }

    getTopics(): Observable<Topic[]> {
        return this.http.get<Topic[]>('/api/topics');
    }
}