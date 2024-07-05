//Cette interface est utilisée pour typer les objets qui contiennent des informations sur une session utilisateur, 
//incluant le jeton d'authentification, le type de jeton, l'identifiant de l'utilisateur, son nom d'utilisateur, 
//et son adresse e-mail.

export interface SessionInformation {
    token: string;
    type: string;
    id: number;
    username: string;
    email: string;
}
