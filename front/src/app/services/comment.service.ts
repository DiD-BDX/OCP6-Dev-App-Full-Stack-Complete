import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentInformation } from '../interfaces/commentInformation.interface';



@Injectable({
    providedIn: 'root'
})
export class CommentService {
    constructor(private http: HttpClient) { }

    getCommentsByPostId(postId: number): Observable<any> {
        return this.http.get(`/api/post/${postId}/comments`);
    }

    createComment(postId: number, comment: CommentInformation): Observable<CommentInformation> {
        return this.http.post<CommentInformation>(`/api/post/${postId}/comments`, comment);
    }

}