// Created by carme
// Creation date 30/11/2021

package com.example.thevault.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Klant extends Gebruiker {
    private String naam;
    private Adres adres;
    private long bsn;
    private Rekening rekening;
    private List<Asset> portefeuille;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate geboortedatum;
    //TODO lijst van transacties toevoegen


    private final Logger logger = LoggerFactory.getLogger(Klant.class);

    public Klant(){
        super();
        logger.info("Lege klant, no args constructor");
    }

    public Klant(int gebruikerID, String gebruikersnaam, String wachtwoord,
                 List<Asset> portefeuille, Rekening rekening,
                 String naam, Adres adres, int BSN, LocalDate geboortedatum) {
        super(gebruikerID, gebruikersnaam, wachtwoord);
        this.naam = naam;
        this.adres = adres;
        this.bsn = bsn;
        this.geboortedatum = geboortedatum;
        this.rekening = rekening;
        this.portefeuille = portefeuille;
        logger.info("New Klant, all args constructor");
    }
    public Klant(String gebruikersnaam, String wachtwoord,
                 String naam, int bsn, LocalDate geboortedatum){
        this(0, gebruikersnaam, wachtwoord, null, null, naam, null, bsn, geboortedatum);
    }
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public long getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public List<Asset> getPortefeuille() {
        return portefeuille;
    }

    public void setPortefeuille(List<Asset> portefeuille) {
        this.portefeuille = portefeuille;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    @Override
    public String toString() {
        return "Klant{" +
                "naam='" + naam + '\'' +
                ", adres=" + adres +
                ", BSN=" + bsn +
                ", rekening=" + rekening +
                ", portefeuille=" + portefeuille +
                ", geboortedatum=" + geboortedatum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Klant)) return false;
        Klant klant = (Klant) o;
        return bsn == klant.bsn && naam.equals(klant.naam) && Objects.equals(adres, klant.adres) && Objects.equals(rekening, klant.rekening) && Objects.equals(portefeuille, klant.portefeuille) && geboortedatum.equals(klant.geboortedatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam, adres, bsn, rekening, portefeuille, geboortedatum);
    }
}
