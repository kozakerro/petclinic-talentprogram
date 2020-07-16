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
package org.springframework.samples.petclinic.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.samples.petclinic.model.Vaccination;
import org.springframework.samples.petclinic.repository.VaccinationRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcVaccinationRepositoryImpl implements VaccinationRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertVaccination;

    @Autowired
    public JdbcVaccinationRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertVaccination = new SimpleJdbcInsert(dataSource)
            .withTableName("vaccinations")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(Vaccination vaccination) throws DataAccessException {
        if (vaccination.isNew()) {
            Number newKey = this.insertVaccination.executeAndReturnKey(
                createVaccinationParameterSource(vaccination));
            vaccination.setId(newKey.intValue());
        } else {
            throw new UnsupportedOperationException("Vaccination update not supported");
        }
    }

    private MapSqlParameterSource createVaccinationParameterSource(Vaccination vaccination) {
        return new MapSqlParameterSource()
            .addValue("id", vaccination.getId())
            .addValue("name", vaccination.getName())
            .addValue("vaccination_date", vaccination.getVaccinationDate())
            .addValue("expiration_date", vaccination.getExpirationDate())
            .addValue("vet_id", vaccination.getVet() != null ? vaccination.getVet().getId() : null)
            .addValue("pet_id", vaccination.getPet() != null ? vaccination.getPet().getId() : null);
    }

    @Override
    public List<Vaccination> findByPetId(Integer petId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", petId);
        JdbcPet pet = this.jdbcTemplate.queryForObject(
                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE id=:id",
                params,
                new JdbcPetRowMapper());

        List<Vaccination> vaccinations = this.jdbcTemplate.query(
            "SELECT id as vaccination_id, name, vaccination_date, expiration_date FROM vaccinations WHERE pet_id=:id",
            params, new JdbcVaccinationRowMapper());

        for (Vaccination vaccination: vaccinations) {
            vaccination.setPet(pet);
        }

        return vaccinations;
    }

}
