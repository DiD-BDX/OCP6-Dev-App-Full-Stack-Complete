// Importation des modules nécessaires depuis Angular et des services et interfaces spécifiques à l'application
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { ArticleSortService } from 'src/app/services/article-sort.service';

// Déclaration du composant avec son sélecteur, son template HTML et son fichier de style
@Component({
  selector: 'app-articles', // Le sélecteur utilisé pour intégrer ce composant dans un template HTML
  templateUrl: './articles.component.html', // Le chemin vers le fichier HTML du composant
  styleUrls: ['./articles.component.scss'] // Le chemin vers le fichier de style du composant
})
export class ArticlesComponent implements OnInit {
  // Déclaration des propriétés du composant
  articles: PostInformation[] = []; // Un tableau pour stocker les articles récupérés
  username: string = ''; // Pour stocker le nom d'utilisateur actuel
  sortDirection: string = 'desc'; // La direction du tri, 'desc' par défaut

  // Le constructeur initialise les services utilisés par le composant
  constructor(
    private postService: PostService, // Service pour récupérer les articles
    private sessionService: SessionService, // Service pour gérer les informations de session
    private articleSortService: ArticleSortService // Service pour trier les articles
  ) { }

  // La méthode ngOnInit est appelée après la création du composant
  ngOnInit(): void {
    this.getArticles(); // Appel de la méthode pour récupérer les articles
    this.username = this.sessionService.getSessionInformation().username; // Récupération du nom d'utilisateur depuis le service de session
  }

  // Méthode pour récupérer les articles en fonction des abonnements de l'utilisateur
  getArticles(): void {
    const userId = this.sessionService.getSessionInformation().id; // Récupération de l'ID de l'utilisateur
    
    // Appel du service PostService pour récupérer les articles, puis stockage des articles dans la propriété `articles`
    this.postService.getPostsByUserSubscriptions(userId)
      .subscribe(articles => this.articles = articles);
  }

  // Méthode pour trier les articles en fonction de la date
  sortArticles(order: string, event: Event): void {
    event.preventDefault(); // Empêche l'action par défaut de l'événement (utile si le tri est déclenché par un formulaire)
    
    // Si l'ordre de tri est basé sur la date, utilise le service ArticleSortService pour trier les articles
    if (order === 'date') {
      this.articles = this.articleSortService.sortArticles(this.articles, this.sortDirection);
      // Inverse la direction du tri pour le prochain appel
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    }
  }
}