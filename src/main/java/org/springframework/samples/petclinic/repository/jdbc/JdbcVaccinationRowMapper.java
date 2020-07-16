/*
 * Copyright 2002-2015 the original author or authors.
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


import org.springframework.jdbc.core.RowMapper;
import org.springframework.samples.petclinic.model.Vaccination;
import org.springframework.samples.petclinic.model.Visit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

class JdbcVaccinationRowMapper implements RowMapper<Vaccination> {

    @Override
    public Vaccination mapRow(ResultSet rs, int row) throws SQLException {
        Vaccination vaccination = new Vaccination();
        vaccination.setId(rs.getInt("vaccination_id"));
        vaccination.setName(rs.getString("name"));
        vaccination.setVaccinationDate(rs.getObject("vaccination_date", LocalDate.class));
        vaccination.setExpirationDate(rs.getObject("expiration_date", LocalDate.class));
        return vaccination;
    }
}
