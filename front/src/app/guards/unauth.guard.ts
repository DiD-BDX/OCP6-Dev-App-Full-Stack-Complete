import { Router } from "@angular/router";
import { SessionService } from "../services/session.service";
import { Injectable } from "@angular/core";



@Injectable({providedIn: 'root'})
export class UnauthGuard  {

    constructor( 
        private router: Router,
        private sessionService: SessionService,
    ) {
    }

    public canActivate(): boolean {
        console.log('UnauthGuard#canActivate called');
        if (this.sessionService.isLogged) {
            console.log('User is logged in, navigating to /me');
            this.router.navigate(['me']);
            return false;
        }
        console.log('User is not logged in, access granted');
        return true;
    }
}