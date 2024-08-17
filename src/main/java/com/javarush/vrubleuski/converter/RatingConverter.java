package com.javarush.vrubleuski.converter;

import com.javarush.vrubleuski.entity.Rating;
import jakarta.persistence.AttributeConverter;

public class RatingConverter implements AttributeConverter<Enum<Rating>, String> {
    @Override
    public String convertToDatabaseColumn(Enum<Rating> attribute) {
        Rating rating = (Rating) attribute;
        String data = switch (rating) {
            case G -> Rating.G.toString();
            case PG -> Rating.PG.toString();
            case PG13 -> Rating.PG13.toString();
            case R -> Rating.R.toString();
            case NC17 -> Rating.NC17.toString();
        };
        return data;
    }

    @Override
    public Enum<Rating> convertToEntityAttribute(String dbData) {
        Rating rating = switch (dbData) {
            case "PG" -> Rating.PG;
            case "PG-13" -> Rating.PG13;
            case "R" -> Rating.R;
            case "NC-17" -> Rating.NC17;
            default -> Rating.G;
        };
        return rating;
    }
}