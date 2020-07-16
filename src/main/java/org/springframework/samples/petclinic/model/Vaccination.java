package org.springframework.samples.petclinic.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccinations")
public class Vaccination extends NamedEntity {

    @Column(name = "vaccination_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate vaccinationDate;

    @Column(name = "expiration_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate expirationDate;

    @OneToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }
    public LocalDate getVaccinationDate() {
        return this.vaccinationDate;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }
    public Vet getVet() {
        return this.vet;
    }
    public void setVet(Vet vet) {
        this.vet = vet;
    }
    public Pet getPet() {
        return this.pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
