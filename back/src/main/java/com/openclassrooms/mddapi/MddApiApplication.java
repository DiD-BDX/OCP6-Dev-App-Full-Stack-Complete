package com.openclassrooms.mddapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application MddApi.
 * <p>
 * Cette classe contient la méthode main qui lance l'application.
 * Elle est annotée avec {@link SpringBootApplication}, indiquant qu'il s'agit d'une application Spring Boot.
 * @see SpringApplication
 * @see SpringBootApplication
 */
@SpringBootApplication
public class MddApiApplication {

    /**
     * Méthode principale de l'application.
     * <p>
     * Cette méthode lance l'application Spring Boot en utilisant {@link SpringApplication#run(Class, String...)}.
     *
     * @param args Les arguments de la ligne de commande.
     * @see SpringApplication#run(Class, String...)
     */
    public static void main(String[] args) {
        SpringApplication.run(MddApiApplication.class, args);
    }

}