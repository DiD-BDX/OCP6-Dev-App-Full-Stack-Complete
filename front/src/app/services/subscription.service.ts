import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject, catchError, of, tap } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { HttpParams } from '@angular/common/http';
import { Topic } from '../interfaces/topicInformation.interface';
import { SubscriptionInformation } from '../interfaces/subscriptionInformation.interface';
import { ITopicService } from '../interfaces/topic.service.interface';
import { ISubscriptionService } from '../interfaces/subscription.service.interface';
import { HttpService } from './http.service'; // Import HttpService

@Injectable({
    providedIn: 'root'
})
export class SubscriptionService implements ITopicService, ISubscriptionService, OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private httpService: HttpService) { } // Use HttpService instead of HttpClient

    // Method to subscribe a user to a topic
    subscribeUserToTopic(topicId: number, userId: number): Observable<any> {
        return this.httpService.post(`/api/topics/${topicId}/subscribe`, { userId: userId }).pipe(
            tap(response => {
                // Handle successful response
            }),
            catchError(error => {
                if (error.status === 409) { // Handle conflict error
                    return of('User is already subscribed to this topic');
                } else {
                    throw error;
                }
            }),
            takeUntil(this.destroy$) // Unsubscribe when destroy$ emits
        );
    }

    // Method to unsubscribe a user from a topic
    unsubscribeUserFromTopic(topicId: number, userId: number): Observable<any> {
        const params = new HttpParams().set('userId', userId.toString());
        return this.httpService.delete(`/api/topics/${topicId}/unsubscribe`, { params }).pipe(
            tap(response => {
                // Handle successful response
            }),
            catchError(error => {
                throw error;
            }),
            takeUntil(this.destroy$) // Unsubscribe when destroy$ emits
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