import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { Topic } from 'src/app/interfaces/topicInformation.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  profileForm = this.createProfileForm();
  topics: Topic[] = [];
  subscribedTopics: SubscriptionInformation[] = [];
  userId: number;

  constructor(private sessionService: SessionService,
              private router: Router,
              private subscriptionService: SubscriptionService
  ) {
    this.userId = this.sessionService.getSessionInformation().id;
    
  }

  ngOnInit(): void {
    this.initializeComponent();
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }
  
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate([''])
  }

  save(): void {
    this.updateSessionInformation();
  }

  unsubscribeFromTopic(topic: Topic) {
    this.unsubscribeUserFromTopic(topic);
  }

  private createProfileForm(): FormGroup {
    return new FormGroup({
      username: new FormControl(''),
      email: new FormControl(''),
    });
  }

  private initializeComponent(): void {
    this.setProfileForm();
    this.getTopics();
    this.getSubscribedTopics();
  }

  private setProfileForm(): void {
    const sessionInformation: SessionInformation = this.sessionService.getSessionInformation();
    if (sessionInformation) {
      this.profileForm.setValue({username: sessionInformation.username, email: sessionInformation.email});
    }
  }

  private getTopics(): void {
    this.subscriptionService.getTopics().subscribe(this.handleTopicsResponse());
  }

  

  private getSubscribedTopics(): void {
    this.subscriptionService.getSubscribedTopics(this.userId).subscribe((subscriptions: SubscriptionInformation[]) => {
      this.subscribedTopics = subscriptions;
    });
  }

  private updateSessionInformation(): void {
    const sessionInformation: SessionInformation = this.sessionService.getSessionInformation();
    const updatedSessionInformation: SessionInformation = {
      ...sessionInformation,
      username: this.profileForm.value.username ?? '',
      email: this.profileForm.value.email ?? ''
    };
    this.sessionService.updateSessionInformation(updatedSessionInformation).subscribe(this.handleUpdateSessionInformationResponse());
  }

  private unsubscribeUserFromTopic(topic: Topic): void {
    this.subscriptionService.unsubscribeUserFromTopic(topic.id, this.userId).subscribe(this.handleUnsubscribeUserFromTopicResponse(topic));
  }

  private handleTopicsResponse(): any {
    return {
      next: (data: Topic[])=> {
        this.topics = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors de la récupération des topics :', error);
      }
    };
  }

  private handleUpdateSessionInformationResponse(): any {
    return {
      next: (updatedSessionInformation: SessionInformation) => {
        console.log('Session information updated', updatedSessionInformation);
      },
      error: (error: any) => {
        console.error('Error updating session information', error);
      }
    };
  }

  private handleUnsubscribeUserFromTopicResponse(topic: Topic): any {
    return {
      next: (response: { message: string }) => {
        console.log('Désinscription réussie' + response);
        const index = this.subscribedTopics.findIndex(subscription => subscription.topic.id === topic.id);
        if (index !== -1) {
          this.subscribedTopics.splice(index, 1);
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors de la désinscription du topic :', error);
      }
    };
}
}