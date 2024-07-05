//Cette interface est utilisée pour typer les objets qui contiennent des informations sur un commentaire, 
//incluant son contenu, les dates de création et de mise à jour, l'identifiant de l'utilisateur qui l'a posté, 
//le nom d'utilisateur de cet utilisateur, et l'identifiant du post associé.

export interface CommentInformation {
    content: string;
    createdAt: Date;
    updatedAt: Date;
    userId: number;
    commentUsername: string;
    postId: number;
}