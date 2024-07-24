import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, catchError, of, tap } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { Topic } from '../interfaces/topicInformation.interface';
import { SubscriptionInformation } from '../interfaces/subscriptionInformation.interface';
import { ITopicService } from '../interfaces/topic.service.interface';
import { ISubscriptionService } from '../interfaces/subscription.service.interface';
import { HttpService } from './http.service'; // Import HttpService
import { SubscriptionActionResult } from '../interfaces/subscriptionActionResult.interface';

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService implements ITopicService, ISubscriptionService, OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private httpService: HttpService) { } // Use HttpService instead of HttpClient

    // Method to subscribe a user to a topic
    subscribeUserToTopic(topicId: number, userId: number): Observable<SubscriptionActionResult> {
        return this.httpService.post<SubscriptionActionResult>(`/api/topics/${topicId}/subscribe`, { userId })
            .pipe(
                tap(response => {
                // Traitement en cas de succès
                }),
                catchError(error => {
                if (error.status === 409) {
                    // Retourne un Observable avec un message spécifique en cas de conflit
                    return of({ message: 'User is already subscribed to this topic' });
                } else {
                    // Propagation de l'erreur si elle n'est pas gérée spécifiquement
                    throw error;
                }
                }),
                takeUntil(this.destroy$) // Se désabonner lorsque destroy$ émet
            );
        }

    // Method to unsubscribe a user from a topic
    unsubscribeUserFromTopic(topicId: number, userId: number): Observable<SubscriptionActionResult> {
        const params = new HttpParams().set('userId', userId.toString());
        return this.httpService.delete<SubscriptionActionResult>(`/api/topics/${topicId}/unsubscribe`, { params })
            .pipe(
                tap(response => {
                // Traitement en cas de succès
                }),
                catchError(error => {
                // Gestion de l'erreur et transformation en un Observable de UnsubscribeResponse
                return of({ message: `Erreur lors de la désinscription : ${error.message}` });
                }),
                takeUntil(this.destroy$) // Se désabonner lorsque destroy$ émet
            );
        }

    // Method to get subscribed topics for a user
    getSubscribedTopics(userId: number): Observable<SubscriptionInformation[]> {
        return this.httpService.get<SubscriptionInformation[]>(`/api/topics/${userId}/subscriptions`).pipe(
            takeUntil(this.destroy$) // Unsubscribe when destroy$ emits
        );
    }

    // Method to get all topics
    getTopics(): Observable<Topic[]> {
        return this.httpService.get<Topic[]>('/api/topics').pipe(
            takeUntil(this.destroy$) // Unsubscribe when destroy$ emits
        );
    }

    // Method called at the end of the component's lifecycle
    // Completes the Subject to unsubscribe all observables
    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}