import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-articles-detail',
  templateUrl: './articles-detail.component.html',
  styleUrls: ['./articles-detail.component.scss']
})
export class ArticlesDetailComponent implements OnInit {
  id: string | null;
  article: PostInformation | null;
  username: string = '';

  constructor(
    private route: ActivatedRoute, 
    private location: Location,
    private sessionService: SessionService,
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
        this.article = articles.find(article => article.id === +this.id!)  || null;
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}