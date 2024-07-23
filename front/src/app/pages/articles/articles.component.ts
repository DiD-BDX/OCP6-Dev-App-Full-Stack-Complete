import { Component, OnInit, OnDestroy } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { ArticleSortService } from 'src/app/services/article-sort.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

/**
 * Déclaration du composant ArticlesComponent.
 * Ce composant est utilisé pour afficher et gérer les articles.
 */
@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit, OnDestroy {
  // Propriété pour stocker les articles récupérés
  articles: PostInformation[] = [];
  // Propriété pour stocker le nom d'utilisateur
  username: string = '';
  // Propriété pour stocker la direction du tri (ascendant ou descendant)
  sortDirection: string = 'desc';
  // Subject utilisé pour notifier la destruction du composant et désabonner les observables
  private destroy$ = new Subject<void>();

  /**
   * Constructeur du composant ArticlesComponent.
   * Injecte les services nécessaires pour récupérer les articles, les informations de session et trier les articles.
   */
  constructor(
    private postService: PostService,
    private sessionService: SessionService,
    private articleSortService: ArticleSortService
  ) { }

  /**
   * Méthode appelée lors de l'initialisation du composant.
   * Récupère les articles et le nom d'utilisateur.
   */
  ngOnInit(): void {
    this.getArticles();
    this.username = this.sessionService.getSessionInformation().username;
  }

  /**
   * Méthode pour récupérer les articles en fonction des abonnements de l'utilisateur.
   */
  getArticles(): void {
    // Récupération de l'ID de l'utilisateur à partir des informations de session
    const userId = this.sessionService.getSessionInformation().id;
    
    // Appel du service PostService pour récupérer les articles, puis stockage des articles dans la propriété `articles`
    this.postService.getPostsByUserSubscriptions(userId)
      .pipe(takeUntil(this.destroy$)) // Utilisation de takeUntil pour désabonner l'observable lorsque le composant est détruit
      .subscribe(articles => this.articles = articles);
    console.log(this.articles);
  }

  /**
   * Méthode pour trier les articles en fonction de la date.
   * @param order L'ordre de tri (par exemple, 'date').
   * @param event L'événement déclenché par l'utilisateur.
   */
  sortArticles(order: string, event: Event): void {
    // Empêche l'action par défaut de l'événement (utile si le tri est déclenché par un formulaire)
    event.preventDefault();
    
    // Si l'ordre de tri est basé sur la date, utilise le service ArticleSortService pour trier les articles
    if (order === 'date') {
      this.articles = this.articleSortService.sortArticles(this.articles, this.sortDirection);
      // Inverse la direction du tri pour le prochain appel
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    }
  }

  /**
   * Méthode appelée lors de la destruction du composant.
   * Notifie la destruction du composant et complète le Subject destroy$ pour désabonner les observables.
   */
  ngOnDestroy(): void {
    this.destroy$.next(); // Émet une valeur pour notifier la destruction du composant
    this.destroy$.complete(); // Complète le Subject pour libérer les ressources
  }
}