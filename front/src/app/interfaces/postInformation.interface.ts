//Cette interface est utilisée pour typer les objets qui contiennent des informations sur un post, 
//incluant son identifiant, titre, contenu, les dates de création et de mise à jour, 
//l'identifiant et le nom d'utilisateur de l'auteur, ainsi que l'identifiant du sujet associé.

export interface PostInformation {
    id: number;
    title: string;
    content: string;
    createdAt: Date;
    updatedAt: Date;
    userId: number;
    postUsername: string;
    topicId: number;
}