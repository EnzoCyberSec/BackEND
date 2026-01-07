package fr.isen.wokandroll.model;


public class LigneCommande {
    private int idLigneCommande;

    private int quantite;

    private double prixUnitaire;

    private Commande commande;

    private Plat plat;

    private Option[] options;

    public int getIdLigneCommande() {
//begin of modifiable zone................T/648cbeed-6b23-4d6b-bd09-49f998f99ac2
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/648cbeed-6b23-4d6b-bd09-49f998f99ac2
//begin of modifiable zone................T/89224b6b-e54b-4256-b5fb-631fd5a7c608
        return this.idLigneCommande;
//end of modifiable zone..................E/89224b6b-e54b-4256-b5fb-631fd5a7c608
    }

    public void setIdLigneCommande(final int value) {
//begin of modifiable zone................T/6cb8de1a-19d8-4a87-a345-0bc2521e558c
        // Automatically generated method. Please delete this comment before entering specific code.
        this.idLigneCommande = value;
//end of modifiable zone..................E/6cb8de1a-19d8-4a87-a345-0bc2521e558c
    }

    public int getQuantite() {
//begin of modifiable zone................T/880cb39e-2406-4d3c-a7c4-f4d99baea1db
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/880cb39e-2406-4d3c-a7c4-f4d99baea1db
//begin of modifiable zone................T/a7f9812e-419b-47e9-9f8c-75e250467266
        return this.quantite;
//end of modifiable zone..................E/a7f9812e-419b-47e9-9f8c-75e250467266
    }

    public void setQuantite(final int value) {
//begin of modifiable zone................T/953eea3e-efdb-4029-836c-f93843068956
        // Automatically generated method. Please delete this comment before entering specific code.
        this.quantite = value;
//end of modifiable zone..................E/953eea3e-efdb-4029-836c-f93843068956
    }

    public double getPrixUnitaire() {
//begin of modifiable zone................T/6046846d-35a5-4466-833d-bef99aca278f
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/6046846d-35a5-4466-833d-bef99aca278f
//begin of modifiable zone................T/2b2a9e80-6a34-4884-b6fc-db519b92526f
        return this.prixUnitaire;
//end of modifiable zone..................E/2b2a9e80-6a34-4884-b6fc-db519b92526f
    }

    public void setPrixUnitaire(final double value) {
//begin of modifiable zone................T/07884569-2a36-4e67-8177-c573f28fb47d
        // Automatically generated method. Please delete this comment before entering specific code.
        this.prixUnitaire = value;
//end of modifiable zone..................E/07884569-2a36-4e67-8177-c573f28fb47d
    }

}
