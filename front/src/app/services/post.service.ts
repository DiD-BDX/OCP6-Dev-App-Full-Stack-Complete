import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { PostInformation } from '../interfaces/postInformation.interface';
import { PostRequest } from '../interfaces/postRequest.interface';
import { FormGroup } from '@angular/forms';
import { SessionService } from './session.service';
import { HttpService } from './http.service';

@Injectable({
    providedIn: 'root'
})
export class PostService implements OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private httpService: HttpService, private sessionService: SessionService) { }

    // Utilise le service générique pour les requêtes GET
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    getPostsByUserSubscriptions(userId: number): Observable<PostInformation[]> {
        return this.httpService.get<PostInformation[]>(`/api/${userId}/posts`).pipe(takeUntil(this.destroy$));
    }

    // Utilise le service générique pour les requêtes POST
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    createPost(userId: number, topicId: number, post: PostRequest): Observable<PostInformation> {
        return this.httpService.post<PostInformation>(`/api/post/create/${userId}/${topicId}`, post).pipe(takeUntil(this.destroy$));
    }

    // Méthode pour soumettre un post à partir d'un formulaire
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    submitPost(form: FormGroup): Observable<any> {
        const sessionInfo = this.sessionService.getSessionInformation();
        const userId = sessionInfo.id;
        const postUsername = sessionInfo.username;
        const topicId = form.get('topic')?.value || 0;
        const post: PostRequest = {
            title: form.get('title')?.value || '',
            content: form.get('content')?.value || '',
            postUsername: postUsername,
            userId: userId,
            topicId: topicId,
            createdAt: new Date(),
            updatedAt: new Date()
        };
        return this.createPost(userId, topicId, post).pipe(takeUntil(this.destroy$));
    }

    // Méthode appelée à la fin de vie du composant
    // Complète le Subject pour désabonner tous les observables
    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}