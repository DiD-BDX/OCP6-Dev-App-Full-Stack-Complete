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
        if (this.sessionService.isLogged) {
        this.router.navigate(['themes']);
        return false;
        }
        return true;
    }
}