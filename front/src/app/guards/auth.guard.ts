import { Injectable } from "@angular/core";
import { SessionService } from "../services/session.service";
import { Router } from "@angular/router";


@Injectable({providedIn: 'root'})
export class AuthGuard  {

    constructor( 
        private router: Router,
        private sessionService: SessionService,
    ) {
    }

    public canActivate(): boolean {
        if (!this.sessionService.isLogged) {
        this.router.navigate(['login']);
        return false;
        }
        return true;
    }
}