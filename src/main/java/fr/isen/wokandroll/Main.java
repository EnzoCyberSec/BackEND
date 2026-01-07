package fr.isen.wokandroll;

import io.javalin.Javalin;
import fr.isen.wokandroll.service.CategorieService;
import fr.isen.wokandroll.service.CategorieServiceImpl;
import fr.isen.wokandroll.model.Categorie;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Classe principale qui démarre le serveur API WokAndRoll
 */
public class Main {

    private static CategorieService categorieService = new CategorieServiceImpl();

    public static void main(String[] args) {
        // Créer et configurer l'application Javalin
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
        }).start(7001);

        // Message de démarrage
        System.out.println("🚀 Serveur WokAndRoll démarré sur http://localhost:7001");
        System.out.println("📋 Page categories : http://localhost:7001/categories");

        // Route GET /categories - récupère toutes les catégories
        app.get("/categories", ctx -> {
            List<Categorie> categories = categorieService.findAll();
            ctx.json(categories);
        });

        // Route GET /categories/{id} - récupère une catégorie par ID
        app.get("/categories/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categorie categorie = categorieService.findById(id);

            if (categorie != null) {
                ctx.json(categorie);
            } else {
                ctx.status(404).result("Catégorie non trouvée");
            }
        });

        // Route GET / - page d'accueil (HTML)
        app.get("/", ctx -> {
            ctx.html(getWelcomeHTML());
        });
    }

    /**
     * Charge la page HTML d'accueil depuis les ressources
     */
    private static String getWelcomeHTML() {
        try {
            // Mets ton fichier HTML (consignes/projet) dans src/main/resources/
            // et adapte le nom ici (par ex. "index.html" ou "wokandroll.html")
            InputStream inputStream = Main.class.getClassLoader()
                    .getResourceAsStream("welcome.html");

            if (inputStream == null) {
                return "<h1>Erreur : Page non trouvée</h1>";
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            return "<h1>Erreur de chargement</h1>";
        }
    }
}
