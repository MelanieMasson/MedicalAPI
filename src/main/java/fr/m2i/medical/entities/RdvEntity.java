package fr.m2i.medical.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rdv", schema = "medical5", catalog = "")
public class RdvEntity {
    private int id;
    private PatientEntity patient;
    private String dateheure;
    private int duree;
    private String note;
    private String type;

    public RdvEntity() {
    }

    public RdvEntity(int id, PatientEntity patient, String dateheure, int duree, String note, String type) {
        this.id = id;
        this.patient = patient;
        this.dateheure = dateheure;
        this.duree = duree;
        this.note = note;
        this.type = type;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dateheure", nullable = false, length = 100)
    public String getDateheure() {return dateheure;}
    public void setDateheure(String dateheure) {this.dateheure = dateheure;}

    @Basic
    @Column(name = "duree", nullable = false)
    public int getDuree() {return duree;}
    public void setDuree(int duree) {this.duree = duree;}

    @Basic
    @Column(name = "note", nullable = false, length = 100)
    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}

    @Basic
    @Column(name = "type", nullable = false, length = 50)
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RdvEntity that = (RdvEntity) o;
        return id == that.id && Objects.equals(dateheure, that.dateheure) && Objects.equals(duree, that.duree) && Objects.equals(note, that.note) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateheure, duree, note, type);
    }

    @OneToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    public PatientEntity getPatient() { return patient; }
    public void setPatient(PatientEntity patient) { this.patient = patient; }
}