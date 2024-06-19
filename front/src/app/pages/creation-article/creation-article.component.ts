import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { FormBuilder, Validators } from '@angular/forms';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { PostService } from 'src/app/services/post.service';
import { PostRequest } from 'src/app/interfaces/postRequest.interface';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';

@Component({
  selector: 'app-creation-article',
  templateUrl: './creation-article.component.html',
  styleUrls: ['./creation-article.component.scss']
})
export class CreationArticleComponent implements OnInit {
  public hide = true;
  public onError = false;
  public postUsername: string = '';
  public subscribedTopics: SubscriptionInformation[] = [];
  public userId = this.sessionService.getSessionInformation().id;

  public form = this.fb.group({
    topic: ['', Validators.required],
    title: ['', Validators.required],
    content: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder, 
    private location: Location, 
    private subscriptionService: SubscriptionService, 
    private sessionService: SessionService, 
    private postService: PostService) {}

  ngOnInit(): void {
    this.getSubscribedTopics();
  }

  getSubscribedTopics(): void {
    
    this.subscriptionService.getSubscribedTopics(this.userId).subscribe((subscriptions: SubscriptionInformation[]) => {
      this.subscribedTopics = subscriptions;
    });
  }
  submit(): void {
    const userId = this.sessionService.getSessionInformation().id;
    const postUsername = this.sessionService.getSessionInformation().username;
    const topicControl = this.form.get('topic');
    const createdAt = new Date();
    const updatedAt = new Date();
    let topicId: number =0;
    if (topicControl) {
      if (topicControl.value !== null) {
        topicId = +topicControl.value;
      }
    }

    const post: PostRequest = {
      title: this.form.get('title')?.value || '',
      content: this.form.get('content')?.value || '',
      postUsername: postUsername,
      userId: userId,
      topicId: topicId,
      createdAt: createdAt,
      updatedAt: updatedAt
    };

    this.postService.createPost(userId, topicId, post).subscribe(() => {
      this.goBack();
    });

  }
  /* submit(): void {
    if (this.form.valid) {
      const topicControl = this.form.get('topic');
      console.log(this.form.value);
      if (topicControl) {
        let topicId: number =0;
        if (topicControl.value !== null) {
          topicId = +topicControl.value;
        }
        const title = this.form.get('title')?.value || '';;
        const content = this.form.get('content')?.value || '';;
        const userId = this.userId;
        const postUsername = this.sessionService.getSessionInformation().username;
        const createdAt = new Date();
        const updatedAt = new Date();
        const post: PostRequest = {
          title: title,
          content: content,
          postUsername: postUsername,
          createdAt: createdAt,
          updatedAt: updatedAt
      };
      console.log("---------post: " + post.title + post.content + post.createdAt + post.updatedAt + post.postUsername);
      console.log("---------userId: " + userId);
      console.log("---------topicId: " + topicId);
      if (topicId !== null) { // Vérifiez si topicId n'est pas null avant de l'envoyer à la méthode createPost
        this.postService.createPost(userId, topicId, post).subscribe(() => {
          this.goBack();
        });
      }
      }
    }
  } */
  
  goBack(): void {
    this.location.back();
  }
}
