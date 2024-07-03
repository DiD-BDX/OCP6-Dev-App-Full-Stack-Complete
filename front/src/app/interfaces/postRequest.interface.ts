//Cette interface est conçue pour être utilisée lors de la création ou de la mise à jour de posts dans une application, 
//fournissant un contrat pour les données nécessaires telles que le titre, le contenu, l'identifiant de l'utilisateur, 
//le nom d'utilisateur de l'auteur, les dates de création et de mise à jour, ainsi que l'identifiant du sujet associé.

export interface PostRequest {
    title: string;
    content: string;
    userId: number;
    postUsername: string;
    createdAt: Date;
    updatedAt: Date;
    topicId: number;
    }