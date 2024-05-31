import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';

@Injectable({
    providedIn: 'root'
})
export class SessionResolver implements Resolve<boolean> {
    constructor(private sessionService: SessionService) {}

    resolve(): Observable<boolean> {
        return this.sessionService.$isLogged();
    }
}