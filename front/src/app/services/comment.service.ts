import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CommentInformation } from '../interfaces/commentInformation.interface';
import { HttpService } from './http.service';

@Injectable({
    providedIn: 'root'
})
export class CommentService implements OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private httpService: HttpService, private http: HttpClient) { }

    // Méthode pour obtenir les commentaires par ID de post
    // Utilise la méthode générique GET
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    getCommentsByPostId(postId: number): Observable<any> {
        return this.httpService.get<any>(`/api/post/${postId}/comments`)
            .pipe(takeUntil(this.destroy$));
    }

    // Méthode pour créer un commentaire
    // Utilise la méthode générique POST
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    createComment(postId: number, comment: CommentInformation): Observable<CommentInformation> {
        return this.httpService.post<CommentInformation>(`/api/post/${postId}/comments`, comment)
            .pipe(takeUntil(this.destroy$));
    }

    // Méthode appelée à la fin de vie du composant
    // Complète le Subject pour désabonner tous les observables
    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}