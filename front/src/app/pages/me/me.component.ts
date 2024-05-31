import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  profileForm = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
  });

  constructor(private sessionService: SessionService,
              private router: Router
  ) { }

  ngOnInit(): void {
    const sessionInformation: SessionInformation = this.sessionService.getSessionInformation();
    if (sessionInformation) {
      this.profileForm.setValue({username: sessionInformation.username, email: sessionInformation.email});
    }
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }
  
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }
  save(): void {
    // Mettre à jour les valeurs de username et email
    console.log(this.profileForm.value);
  }
}
