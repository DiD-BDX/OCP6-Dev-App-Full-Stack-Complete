import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-articles-detail',
  templateUrl: './articles-detail.component.html',
  styleUrls: ['./articles-detail.component.scss']
})
export class ArticlesDetailComponent implements OnInit {
  id: string | null;
  article: PostInformation | null;
  username: string = '';
  topicName: string = '';

  constructor(
    private route: ActivatedRoute, 
    private location: Location,
    private sessionService: SessionService,
    private subscriptionService: SubscriptionService,
    private postService: PostService
  ) {
    this.id = '';
    this.article = null;
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getArticleById();
    this.username = this.sessionService.getSessionInformation().username;
  }

  getArticleById(): void {
    const userId = this.sessionService.getSessionInformation().id;
  
    if (this.id) {
      this.postService.getPostsByUserSubscriptions(userId)
        .subscribe((articles: PostInformation[]) => {
          const article = articles.find(article => article.id === +this.id!);
          if (article) {
            this.subscriptionService.getSubscribedTopics(userId).subscribe((topics: SubscriptionInformation[]) => {
              const topic = topics.find(topic => topic.topicId === article.topicId);
              this.article = {...article};
              this.topicName = topic ? topic.topicName : '';
            });
          } else {
            this.article = null;
          }
        });
    }
  }

  goBack(): void {
    this.location.back();
  }
}