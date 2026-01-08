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

    public Commande getCommande() {
//begin of modifiable zone................T/b653f6d0-628c-4bac-9e29-2753188b93a4
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/b653f6d0-628c-4bac-9e29-2753188b93a4
//begin of modifiable zone................T/40ee1145-0702-40a3-bd2f-1b1836c34b5b
        return this.commande;
//end of modifiable zone..................E/40ee1145-0702-40a3-bd2f-1b1836c34b5b
    }

    public void setCommande(final Commande value) {
//begin of modifiable zone................T/59371f08-cc02-4d07-8a4a-5fccbfd576db
        // Automatically generated method. Please delete this comment before entering specific code.
        this.commande = value;
//end of modifiable zone..................E/59371f08-cc02-4d07-8a4a-5fccbfd576db
    }

    public Plat getPlat() {
//begin of modifiable zone................T/1c942f37-6dba-4a38-a268-540ef4a180ec
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/1c942f37-6dba-4a38-a268-540ef4a180ec
//begin of modifiable zone................T/7072419e-eb19-4249-88d7-76e75bd46e40
        return this.plat;
//end of modifiable zone..................E/7072419e-eb19-4249-88d7-76e75bd46e40
    }

    public void setPlat(final Plat value) {
//begin of modifiable zone................T/88f69e96-6d4f-4727-b841-2771d7fbe886
        // Automatically generated method. Please delete this comment before entering specific code.
        this.plat = value;
//end of modifiable zone..................E/88f69e96-6d4f-4727-b841-2771d7fbe886
    }

    public Option[] getOptions() {
//begin of modifiable zone................T/731abe07-e9c7-427b-8dbe-a82207f5ce55
        // Automatically generated method. Please delete this comment before entering specific code.
//end of modifiable zone..................E/731abe07-e9c7-427b-8dbe-a82207f5ce55
//begin of modifiable zone................T/b34212cf-a2ff-4308-924c-e55ec0d0dd18
        return this.options;
//end of modifiable zone..................E/b34212cf-a2ff-4308-924c-e55ec0d0dd18
    }

    public void setOptions(final Option[] value) {
//begin of modifiable zone................T/754b3748-3275-4112-881c-48e81bad8f60
        // Automatically generated method. Please delete this comment before entering specific code.
        this.options = value;
//end of modifiable zone..................E/754b3748-3275-4112-881c-48e81bad8f60
    }

}
