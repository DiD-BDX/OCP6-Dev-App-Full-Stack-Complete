// Importation des modules nécessaires pour le composant
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; // Pour accéder aux paramètres de l'URL
import { Location } from '@angular/common'; // Pour interagir avec l'historique du navigateur
// Importation des services et interfaces nécessaires
import { PostService } from 'src/app/services/post.service';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { SubscriptionInformation } from 'src/app/interfaces/subscriptionInformation.interface';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { CommentInformation } from 'src/app/interfaces/commentInformation.interface';
import { CommentService } from 'src/app/services/comment.service';

// Déclaration du composant avec son sélecteur, template HTML et fichier CSS
@Component({
  selector: 'app-articles-detail',
  templateUrl: './articles-detail.component.html',
  styleUrls: ['./articles-detail.component.scss']
})
export class ArticlesDetailComponent implements OnInit {
  // Déclaration des variables pour stocker les informations nécessaires
  id: string | null;
  article: PostInformation | null;
  username: string = '';
  topicName: string = '';
  comments: CommentInformation[] = [];
  commentUsername: string = '';
  commentContent: string = '';
  errorMessage: string = '';

  // Injection des services nécessaires dans le constructeur
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

  // Méthode ngOnInit appelée à l'initialisation du composant
  ngOnInit() {
    // Récupération de l'ID de l'article depuis l'URL
    this.id = this.route.snapshot.paramMap.get('id');
    // Appel de la méthode pour charger l'article
    this.getArticleById();
    // Récupération du nom d'utilisateur depuis le service de session
    this.username = this.sessionService.getSessionInformation().username;
    // Si un ID est présent, charger les commentaires de l'article
    if (this.id) {
      this.getCommentsByPostId(+this.id);
    }
  }

  // Méthode pour récupérer les détails de l'article
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

  // Méthode pour récupérer les commentaires d'un article
  getCommentsByPostId(postId: number): void {
    this.commentService.getCommentsByPostId(postId)
      .subscribe((comments: CommentInformation[]) => {
        this.comments = comments;
      });
  }

  // Méthode pour ajouter un commentaire à l'article
  addComment(): void {
    if (!this.commentContent.trim()) {
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

  // Méthode pour revenir à la page précédente
  goBack(): void {
    this.location.back();
  }
}