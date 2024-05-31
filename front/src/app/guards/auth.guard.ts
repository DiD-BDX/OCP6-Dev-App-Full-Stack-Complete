import { Injectable } from "@angular/core";
import { SessionService } from "../services/session.service";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { map, take } from "rxjs/operators";


@Injectable({providedIn: 'root'})
export class AuthGuard  {

    constructor( 
        private router: Router,
        private sessionService: SessionService,
    ) {
    }

    public canActivate(): Observable<boolean> {
        return this.sessionService.$isLogged().pipe(
            take(1),
            map(isLogged => {
                if (!isLogged) {
                    this.router.navigate(['login']);
                    return false;
                }
                return true;
            })
        );
    }
}