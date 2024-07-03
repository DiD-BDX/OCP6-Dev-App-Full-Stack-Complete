import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostInformation } from '../interfaces/postInformation.interface';
import { PostRequest } from '../interfaces/postRequest.interface';
import { FormGroup } from '@angular/forms';
import { SessionService } from './session.service';

@Injectable({
    providedIn: 'root'
})
export class PostService {
    constructor(private http: HttpClient, private sessionService: SessionService) { }

    getPostsByUserSubscriptions(userId: number): Observable<any> {
        return this.http.get(`/api/${userId}/posts`);
    }

    createPost(userId: number, topicId: number, post: PostRequest): Observable<PostInformation> {
        return this.http.post<PostInformation>(`/api/post/create/${userId}/${topicId}`, post);
    }

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