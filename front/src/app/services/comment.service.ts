import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentInformation } from '../interfaces/commentInformation.interface';
import { HttpService } from './http.service';

@Injectable({
    providedIn: 'root'
})
export class CommentService {
    constructor(private httpService: HttpService, private http: HttpClient) { }

    // Méthode pour obtenir les commentaires par ID de post
    // Utilise la méthode générique GET
    getCommentsByPostId(postId: number): Observable<any> {
        return this.httpService.get<any>(`/api/post/${postId}/comments`);
    }

    // Méthode pour créer un commentaire
    // Utilise la méthode générique POST
    createComment(postId: number, comment: CommentInformation): Observable<CommentInformation> {
        return this.httpService.post<CommentInformation>(`/api/post/${postId}/comments`, comment);
    }
}