import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostInformation } from '../interfaces/postInformation.interface';
import { PostRequest } from '../interfaces/postRequest.interface';

@Injectable({
    providedIn: 'root'
})
export class PostService {
    constructor(private http: HttpClient) { }

    getPostsByUserSubscriptions(userId: number): Observable<any> {
        return this.http.get(`/api/${userId}/posts`);
    }

    createPost(userId: number, topicId: number, post: PostRequest): Observable<PostInformation> {
        return this.http.post<PostInformation>(`/api/post/create/${userId}/${topicId}`, post);
    }

}