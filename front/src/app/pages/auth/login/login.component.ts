import { Component } from '@angular/core';
import { FormBuilder, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { LoginRequest } from 'src/app/interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { NavigationEnd, Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Observable, filter, map } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public hide = true;
  public onError = false;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        this.emailOrUsername
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3)
      ]
    ]
  });

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
                this.form = this.fb.group({
                  email: ['', [Validators.required, this.emailOrUsername]],
                  password: ['', Validators.required]
                });
              }

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: SessionInformation) => {
        this.sessionService.logIn(response);
        this.router.navigate(['/me']);
      },
      error: error => this.onError = true,
    });
  }

  private emailOrUsername(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    const validEmail = Validators.email(control) === null;
    const validUsername = value.trim().length > 3;
    return validEmail || validUsername ? null : { 'emailOrUsername': true };
  }
}