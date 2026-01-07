package fr.isen.wokandroll;

import io.javalin.Javalin;
import fr.isen.wokandroll.service.CategorieService;
import fr.isen.wokandroll.service.CategorieServiceImpl;
import fr.isen.wokandroll.service.PlatService;
import fr.isen.wokandroll.service.PlatServiceImpl;
import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.model.Plat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Classe principale qui démarre le serveur API WokAndRoll
 */
public class Main {

    private static CategorieService categorieService = new CategorieServiceImpl();
    private static PlatService platService = new PlatServiceImpl();

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
        System.out.println("📋 Page plats : http://localhost:7001/plats");

        // Route GET /categories - récupère toutes les catégories
        app.get("/categories", ctx -> {
            List<Categorie> categories = categorieService.findAll();
            ctx.json(categories);
        });

        // Route GET /categories/{id} - récupère une catégorie via l'id
        app.get("/categories/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categorie categorie = categorieService.findById(id);

            if (categorie != null) {
                ctx.json(categorie);
            } else {
                ctx.status(404).result("Catégorie non trouvée");
            }
        });

        // Route GET /plats - récupère tous les plats
        app.get("/plats", ctx -> {
            List<Plat> plats = platService.findAll();
            ctx.json(plats);
        });

        // Route GET /plats/{id} - récupère un plat via l'id
        app.get("/plats/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Plat plat = platService.findById(id);

            if (plat != null) {
                ctx.json(plat);
            } else {
                ctx.status(404).result("Plat non trouvé");
            }
        });

        // Route GET /categories/{id}/plats - récupère les plats d'une catégorie
        app.get("/categories/{id}/plats", ctx -> {
            int idCategorie = Integer.parseInt(ctx.pathParam("id"));
            List<Plat> plats = platService.findByCategorie(idCategorie);
            ctx.json(plats);
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
