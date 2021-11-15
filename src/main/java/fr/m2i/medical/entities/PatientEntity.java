package fr.m2i.medical.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "patient", schema = "medical5", catalog = "")
public class PatientEntity {
    private int id;
    private String nom;
    private String prenom;
    private Date datenaissance;
    private String adresse;
    private VilleEntity ville;
    private String mail;

    public PatientEntity(int id, String nom, String prenom, Date datenaissance, String adresse, VilleEntity ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.adresse = adresse;
        this.ville = ville;
        this.mail = mail;
    }

    public PatientEntity() {

    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 100)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "prenom", nullable = false, length = 100)
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Basic
    @Column(name = "datenaissance", nullable = false)
    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    @Basic
    @Column(name = "adresse", nullable = false, length = 100)
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Basic
    @Column(name = "mail", nullable = false, length = 100)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEntity that = (PatientEntity) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(datenaissance, that.datenaissance) && Objects.equals(adresse, that.adresse) && Objects.equals(mail, that.mail);
    }

    @Override
    public String toString() {
        return "PatientEntity{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", datenaissance=" + datenaissance +
                ", adresse='" + adresse + '\'' +
                ", ville=" + ville +
                ", mail=" + mail +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, datenaissance, adresse, mail);
    }

    @OneToOne
    @JoinColumn(name = "ville", referencedColumnName = "id", nullable = false)
    public VilleEntity getVille() {
        return ville;
    }

    public void setVille(VilleEntity ville) {
        this.ville = ville;
    }
}