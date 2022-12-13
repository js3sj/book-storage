package com.assessment.services;

import com.assessment.utils.Error;
import com.assessment.utils.FormAttribute;
import org.springframework.ui.Model;

public class FormValidationService {

    public FormValidationService() {};

    public boolean getErrorsFromAntiqueBookAndScienceJournalFormFields(FormAttribute formAttribute, Model model) {
        if ((formAttribute.getReleaseYear() != null) && (formAttribute.getScienceIndex() == null)) {
            if (!formAttribute.checkReleaseYear()) {
                model.addAttribute("error_message", Error.ERROR_FIELD_RELEASE_YEAR);
                return true;
            }
        }
        if ((formAttribute.getReleaseYear() == null) && (formAttribute.getScienceIndex() != null)){
            if (!formAttribute.checkScienceIndex()) {
                model.addAttribute("error_message", Error.ERROR_FIELD_SCIENCE_INDEX);
                return true;
            }
        }
        if ((formAttribute.getReleaseYear() != null) && (formAttribute.getScienceIndex() != null)) {
            model.addAttribute("error_message", Error.ERROR_FIELD_RELEASE_YEAR_AND_SCIENCE_INDEX);
            return true;
        }
        return false;
    }
}
