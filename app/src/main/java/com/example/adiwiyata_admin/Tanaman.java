package com.example.adiwiyata_admin;



public class Tanaman {
    private String id;
    private String nama;
    private String latin;
    private String imageUrl;
    private String kingdom;
    private String clade;
    private String order;
    private String family;
    private String genus;
    private String species;
    private String deskripsi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public String getClade() {
        return clade;
    }

    public void setClade(String clade) {
        this.clade = clade;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi= deskripsi;
    }

    public Tanaman(String id, String nama, String latin, String imageUrl, String kingdom, String clade, String order, String family, String genus, String species, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.latin = latin;
        this.imageUrl = imageUrl;
        this.kingdom = kingdom;
        this.clade = clade;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.deskripsi = deskripsi;
    }
    public Tanaman(String nama, String latin, String imageUrl, String kingdom, String clade, String order, String family, String genus, String species, String deskripsi) {
        this.nama = nama;
        this.latin = latin;
        this.imageUrl = imageUrl;
        this.kingdom = kingdom;
        this.clade = clade;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.deskripsi = deskripsi;
    }

    public Tanaman(String id, String nama, String latin, String imageUrl)
    {
        this.id = id;
        this.nama = nama;
        this.latin = latin;
        this.imageUrl = imageUrl;
    }
}





