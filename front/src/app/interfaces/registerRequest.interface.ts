//Cette interface est utilisée pour typer les objets qui représentent une demande d'inscription, 
//contenant les informations nécessaires telles que l'adresse e-mail, le nom d'utilisateur, 
//et le mot de passe de l'utilisateur s'inscrivant.

export interface RegisterRequest {
    email: string;
    username: string;
    password: string;
}
