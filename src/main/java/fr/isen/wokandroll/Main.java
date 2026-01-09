package fr.isen.wokandroll;

import fr.isen.wokandroll.service.impl.*;
import io.javalin.Javalin;

import fr.isen.wokandroll.service.CategorieService;
import fr.isen.wokandroll.service.PlatService;
import fr.isen.wokandroll.service.OptionService;
import fr.isen.wokandroll.service.CommandeService;
import fr.isen.wokandroll.service.LigneCommandeService;

import fr.isen.wokandroll.model.Categorie;
import fr.isen.wokandroll.model.Plat;
import fr.isen.wokandroll.model.Option;
import fr.isen.wokandroll.model.Commande;
import fr.isen.wokandroll.model.LigneCommande;

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
    private static OptionService optionService = new OptionServiceImpl();
    private static CommandeService commandeService = new CommandeServiceImpl();
    private static LigneCommandeService ligneCommandeService = new LigneCommandeServiceImpl();
    private static fr.isen.wokandroll.service.StatistiqueService statistiqueService = new StatistiqueServiceImpl();

    public static void main(String[] args) {
        // Création et configuration l'application Javalin
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
        }).start(7001);

        // Message de démarrage
        System.out.println("🚀 Serveur WokAndRoll démarré sur http://localhost:7001");
        System.out.println("📋 Page categories : http://localhost:7001/categories");
        System.out.println("📋 Page plats : http://localhost:7001/plats");
        System.out.println("📋 Page options : http://localhost:7001/options");
        System.out.println("📋 Options d'un plat : http://localhost:7001/plats/{id}/options");
        System.out.println("📋 Commandes : http://localhost:7001/commandes");
        System.out.println("📋 Lignes d'une commande : http://localhost:7001/commandes/{id}/lignes");

        // =======================
        //        CATEGORIES
        // =======================

        // GET /categories - toutes les catégories
        app.get("/categories", ctx -> {
            List<Categorie> categories = categorieService.findAll();
            ctx.json(categories);
        });

        // GET /categories/{id} - une catégorie par id
        app.get("/categories/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Categorie categorie = categorieService.findById(id);

            if (categorie != null) {
                ctx.json(categorie);
            } else {
                ctx.status(404).result("Catégorie non trouvée");
            }
        });

        // =======================
        //          PLATS
        // =======================

        // GET /plats - tous les plats
        app.get("/plats", ctx -> {
            List<Plat> plats = platService.findAll();
            ctx.json(plats);
        });

        // GET /plats/{id} - un plat par id
        app.get("/plats/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Plat plat = platService.findById(id);

            if (plat != null) {
                ctx.json(plat);
            } else {
                ctx.status(404).result("Plat non trouvé");
            }
        });

        // GET /categories/{id}/plats - plats d'une catégorie
        app.get("/categories/{id}/plats", ctx -> {
            int idCategorie = Integer.parseInt(ctx.pathParam("id"));
            List<Plat> plats = platService.findByCategorie(idCategorie);
            ctx.json(plats);
        });

        // =======================
        //         OPTIONS
        // =======================

        // GET /options - toutes les options
        app.get("/options", ctx -> {
            List<Option> options = optionService.findAll();
            ctx.json(options);
        });

        // GET /plats/{id}/options - options d'un plat
        app.get("/plats/{id}/options", ctx -> {
            int idPlat = Integer.parseInt(ctx.pathParam("id"));
            List<Option> options = optionService.findByPlat(idPlat);
            ctx.json(options);
        });

        // =======================
        //        COMMANDES
        // =======================

        // GET /commandes - toutes les commandes
        app.get("/commandes", ctx -> {
            List<Commande> commandes = commandeService.findAll();
            ctx.json(commandes);
        });

        // GET /commandes/{id} - une commande par id
        app.get("/commandes/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Commande commande = commandeService.findById(id);

            if (commande != null) {
                ctx.json(commande);
            } else {
                ctx.status(404).result("Commande non trouvée");
            }
        });

        // GET /commandes/{id}/lignes - lignes d'une commande
        app.get("/commandes/{id}/lignes", ctx -> {
            int idCommande = Integer.parseInt(ctx.pathParam("id"));
            List<LigneCommande> lignes = ligneCommandeService.findByCommande(idCommande);
            ctx.json(lignes);
        });

        // POST /commandes - créer une nouvelle commande
        app.post("/commandes", ctx -> {
            Commande commande = ctx.bodyAsClass(Commande.class);
            Commande created = commandeService.creerCommande(commande);
            ctx.status(201).json(created);
        });

        // POST /lignes - créer une ligne de commande
        app.post("/lignes", ctx -> {
            LigneCommande ligne = ctx.bodyAsClass(LigneCommande.class);
            LigneCommande created = ligneCommandeService.creerLigneCommande(ligne);
            ctx.status(201).json(created);
        });

        // =======================
        //      STATISTIQUES
        // =======================

        // GET /stats/commandes/count - Nombre total de commandes
        app.get("/stats/commandes/count", ctx -> {
            long count = statistiqueService.compterCommandes();
            // On renvoie un petit objet JSON simple
            ctx.json(java.util.Map.of("nombre_commandes", count));
        });

        // GET /stats/panier-moyen - Montant moyen des commandes
        app.get("/stats/panier-moyen", ctx -> {
            double moyenne = statistiqueService.calculerPanierMoyen();
            ctx.json(java.util.Map.of("panier_moyen", moyenne));
        });

        // GET /stats/top-plats - Les plats les plus vendus (défaut 3)
        app.get("/stats/top-plats", ctx -> {
            String limitParam = ctx.queryParam("limit");
            int limit = (limitParam != null) ? Integer.parseInt(limitParam) : 3;

            List<Plat> tops = statistiqueService.trouverPlatsLesPlusVendus(limit);
            ctx.json(tops);
        });

        // =======================
        //        ACCUEIL HTML
        // =======================

        // GET / - page d'accueil (HTML)
        app.get("/", ctx -> {
            ctx.html(getWelcomeHTML());
        });
    }

    /**
     * Charge la page HTML d'accueil
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
