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
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vaccination;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class VaccinationController {

    private final ClinicService clinicService;

    @Autowired
    public VaccinationController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("vaccination")
    public Vaccination loadPetWithVaccination(@PathVariable("petId") int petId) {
        Pet pet = this.clinicService.findPetById(petId);
        Vaccination vaccination = new Vaccination();
        pet.addVaccination(vaccination);
        return vaccination;
    }

    // Spring MVC calls method loadPetWithVaccination(...) before initNewVaccinationForm is called
    @RequestMapping(value = "/owners/*/pets/{petId}/vaccinations/new", method = RequestMethod.GET)
    public String initNewVaccinationForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVaccinationForm";
    }

    // Spring MVC calls method loadPetWithVaccination(...) before processNewVaccinationForm is called
    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/vaccinations/new", method = RequestMethod.POST)
    public String processNewVaccinationForm(@Valid Vaccination vaccination, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVaccinationForm";
        } else {
            this.clinicService.saveVaccination(vaccination);
            return "redirect:/owners/{ownerId}";
        }
    }

    @RequestMapping(value = "/owners/*/pets/{petId}/vaccinations", method = RequestMethod.GET)
    public String showVaccinations(@PathVariable int petId, Map<String, Object> model) {
        model.put("vaccinations", this.clinicService.findPetById(petId).getVaccinations());
        return "vaccinationList";
    }

}
