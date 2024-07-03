//Cette interface est utilisée pour typer les objets qui représentent une demande de connexion, 
//contenant les informations nécessaires telles que l'adresse e-mail et le mot de passe de l'utilisateur.

export interface LoginRequest {
    email: string;
    password: string;
}
