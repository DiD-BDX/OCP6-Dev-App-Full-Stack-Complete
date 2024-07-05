import { Injectable } from '@angular/core';
import { PostInformation } from 'src/app/interfaces/postInformation.interface';

// Fonction de comparaison pour le tri ascendant
// Compare deux objets PostInformation en fonction de leur date de création (createdAt)
// Retourne 1 si a est plus récent que b, sinon -1
function ascendingComparator(a: PostInformation, b: PostInformation): number {
    return (a.createdAt > b.createdAt) ? 1 : -1;
}

// Fonction de comparaison pour le tri descendant
// Compare deux objets PostInformation en fonction de leur date de création (createdAt)
// Retourne 1 si a est plus ancien que b, sinon -1
function descendingComparator(a: PostInformation, b: PostInformation): number {
    return (a.createdAt < b.createdAt) ? 1 : -1;
}

@Injectable({
    providedIn: 'root'
})
export class ArticleSortService {
    // Méthode pour trier une liste d'articles en fonction de la direction spécifiée
    // Prend en entrée un tableau d'articles et une direction de tri ('asc' ou 'desc')
    // Utilise la fonction de comparaison appropriée pour trier les articles
    sortArticles(articles: PostInformation[], direction: string): PostInformation[] {
        // Sélectionne la fonction de comparaison en fonction de la direction
        const comparator = direction === 'asc' ? ascendingComparator : descendingComparator;
        // Trie les articles en utilisant la fonction de comparaison sélectionnée
        return articles.sort(comparator);
    }
}