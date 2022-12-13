package com.assessment.utils;

public class Error {
    public static final String ERROR_FIELD_RELEASE_YEAR = "Field [Release year] must be in range: (0 - 1900).";
    public static final String ERROR_FIELD_SCIENCE_INDEX = "Field [Science index] must be in range: (1 - 10).";
    public static final String ERROR_FIELD_RELEASE_YEAR_AND_SCIENCE_INDEX
            = "\nFields [Release year] and [Science index] can not be filled both.";
    public static final String ERROR_BARCODE_EXIST_IN_DB
            = "\nBook with entered barcode already exists in the database.";
}
