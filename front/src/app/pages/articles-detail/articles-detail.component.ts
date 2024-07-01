import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { CommentInformation } from 'src/app/interfaces/commentInformation.interface';
import { CommentService } from 'src/app/services/comment.service';

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
  comments: CommentInformation[] = [];
  commentUsername: string = '';
  commentContent: string = '';
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute, 
    private location: Location,
    private sessionService: SessionService,
    private subscriptionService: SubscriptionService,
    private commentService: CommentService,
    private postService: PostService
  ) {
    this.id = '';
    this.article = null;
  }

  ngOnInit() {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getArticleById();
    this.username = this.sessionService.getSessionInformation().username;
    if (this.id) {
      this.getCommentsByPostId(+this.id);
    }
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

  getCommentsByPostId(postId: number): void {
    this.commentService.getCommentsByPostId(postId)
      .subscribe((comments: CommentInformation[]) => {
        this.comments = comments;
      });
  }

  addComment(): void {
    if (!this.commentContent.trim()) {
      // Afficher un message d'erreur ou gérer le cas d'un contenu vide
      console.error('Le contenu du commentaire ne peut pas être vide.');
      return;
    }
    const userId = this.sessionService.getSessionInformation().id;
    const postId = +this.id!;
    const comment: CommentInformation = {
      userId: userId,
      postId: postId,
      commentUsername: this.commentUsername,
      content: this.commentContent,
      createdAt: new Date(),
      updatedAt: new Date()
    };

    this.commentService.createComment(postId, comment)
      .subscribe((comment: CommentInformation) => {
        this.comments.push(comment);
        this.commentUsername = '';
        this.commentContent = '';
      });
  }

  goBack(): void {
    this.location.back();
  }
}