package com.example.parkapp.models;

public class HistoryTicket {
    private int id;
    private String datefrom;
    private String dateTo;
    private Float montant;

    public HistoryTicket(int id, String datefrom, String dateTo, float montant) {
        this.id = id;
        this.datefrom = datefrom;
        this.dateTo = dateTo;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(String datefrom) {
        this.datefrom = datefrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }
}
