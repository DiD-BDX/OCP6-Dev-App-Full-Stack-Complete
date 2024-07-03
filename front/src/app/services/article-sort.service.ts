import { Injectable } from '@angular/core';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';

@Injectable({
    providedIn: 'root'
})
export class ArticleSortService {
    sortArticles(articles: PostInformation[], direction: string): PostInformation[] {
        return articles.sort((a, b) => {
        if (direction === 'asc') {
            return (a.createdAt > b.createdAt) ? 1 : -1;
        } else {
            return (a.createdAt < b.createdAt) ? 1 : -1;
        }
        });
    }
}