/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jpa;

import org.springframework.samples.petclinic.model.Vaccination;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VaccinationRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaVaccinationRepositoryImpl implements VaccinationRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Vaccination vaccination) {
        if (vaccination.getId() == null) {
            this.em.persist(vaccination);
        } else {
            this.em.merge(vaccination);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Vaccination> findByPetId(Integer petId) {
        Query query = this.em.createQuery("SELECT v FROM Vaccination v where v.pet.id= :id");
        query.setParameter("id", petId);
        return query.getResultList();
    }

}
