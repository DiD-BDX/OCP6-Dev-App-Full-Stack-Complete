import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostInformation } from '../interfaces/postInformation.interface';
import { PostRequest } from '../interfaces/postRequest.interface';
import { FormGroup } from '@angular/forms';
import { SessionService } from './session.service';
import { HttpService } from './http.service';

@Injectable({
    providedIn: 'root'
})
export class PostService {
    constructor(private httpService: HttpService, private sessionService: SessionService) { }

    // Utilise le service générique pour les requêtes GET
    getPostsByUserSubscriptions(userId: number): Observable<any> {
        return this.httpService.get<any>(`/api/${userId}/posts`);
    }

    // Utilise le service générique pour les requêtes POST
    createPost(userId: number, topicId: number, post: PostRequest): Observable<PostInformation> {
        return this.httpService.post<PostInformation>(`/api/post/create/${userId}/${topicId}`, post);
    }

    // Méthode pour soumettre un post à partir d'un formulaire
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

        return this.createPost(userId, topicId, post);
    }
}