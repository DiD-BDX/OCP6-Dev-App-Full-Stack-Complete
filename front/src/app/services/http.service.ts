import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class HttpService {
    constructor(private http: HttpClient) { }

    // Méthode générique pour les requêtes GET
    get<T>(url: string): Observable<T> {
        return this.http.get<T>(url);
    }

    // Méthode générique pour les requêtes POST
    post<T>(url: string, body: any): Observable<T> {
        return this.http.post<T>(url, body);
    }

    // Méthode générique pour les requêtes PUT
    put<T>(url: string, body: any): Observable<T> {
        return this.http.put<T>(url, body);
    }

    // Méthode générique pour les requêtes DELETE
    delete<T>(url: string, options?: { params?: HttpParams }): Observable<T> {
        return this.http.delete<T>(url, options);
    }
}