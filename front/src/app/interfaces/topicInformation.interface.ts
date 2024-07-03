//Cette interface Topic est utilisée pour typer les objets qui représentent un sujet, 
//avec des informations telles que l'identifiant unique du sujet, son nom, et une description de celui-ci.

export interface Topic {
    id: number;
    name: string;
    description: string;
}