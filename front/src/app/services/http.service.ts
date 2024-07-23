import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class HttpService implements OnDestroy {
    private destroy$ = new Subject<void>();

    constructor(private http: HttpClient) { }

    // Méthode générique pour les requêtes GET
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    get<T>(url: string): Observable<T> {
        return this.http.get<T>(url).pipe(takeUntil(this.destroy$));
    }

    // Méthode générique pour les requêtes POST
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    post<T>(url: string, body: any): Observable<T> {
        return this.http.post<T>(url, body).pipe(takeUntil(this.destroy$));
    }

    // Méthode générique pour les requêtes PUT
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    put<T>(url: string, body: any): Observable<T> {
        return this.http.put<T>(url, body).pipe(takeUntil(this.destroy$));
    }

    // Méthode générique pour les requêtes DELETE
    // Ajoute takeUntil pour désabonner à la fin de vie du composant
    delete<T>(url: string, options?: { params?: HttpParams }): Observable<T> {
        return this.http.delete<T>(url, options).pipe(takeUntil(this.destroy$));
    }

    // Méthode appelée à la fin de vie du composant
    // Complète le Subject pour désabonner tous les observables
    ngOnDestroy(): void {
        this.destroy$.next();
        this.destroy$.complete();
    }
}