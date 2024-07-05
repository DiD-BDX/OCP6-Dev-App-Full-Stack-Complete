// Importe les éléments nécessaires depuis le module '@angular/core'
import { Component, OnInit } from '@angular/core';

// Décorateur @Component qui indique que la classe juste en dessous est un composant Angular
@Component({
  selector: 'app-home', // Le sélecteur CSS pour utiliser ce composant, permet d'insérer le composant dans un template HTML avec la balise <app-home></app-home>
  templateUrl: './home.component.html', // Chemin vers le fichier template HTML du composant
  styleUrls: ['./home.component.scss'], // Chemin(s) vers le(s) fichier(s) de styles CSS/SCSS du composant
})
// Déclaration de la classe HomeComponent qui implémente l'interface OnInit
export class HomeComponent implements OnInit {
  // Constructeur de la classe, utilisé pour l'injection de dépendances
  constructor() {}

  // Méthode ngOnInit(): void
  // Cette méthode est un hook du cycle de vie d'un composant Angular, appelée juste après la création du composant
  // Ici, elle est vide car il n'y a pas de logique d'initialisation spécifique à exécuter au moment de la création du composant
  ngOnInit(): void {}
}