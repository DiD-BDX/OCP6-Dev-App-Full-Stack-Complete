import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {
  articles: PostInformation[] = [];
  username: string = '';

  constructor(private postService: PostService, private sessionService: SessionService) { }

  ngOnInit(): void {
    this.getArticles();
    this.username = this.sessionService.getSessionInformation().username;
  }

  getArticles(): void {
    const userId = this.sessionService.getSessionInformation().id;
    
    this.postService.getPostsByUserSubscriptions(userId)
      .subscribe(articles => this.articles = articles);
  }

  sortArticles(order: string): void {
    if (order === 'desc') {
      this.articles.sort((a, b) => (a.createdAt < b.createdAt) ? 1 : -1);
    } else {
      this.articles.sort((a, b) => (a.createdAt > b.createdAt) ? 1 : -1);
    }
  }
}