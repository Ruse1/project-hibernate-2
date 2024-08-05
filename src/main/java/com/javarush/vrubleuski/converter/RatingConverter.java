package com.javarush.vrubleuski.converter;

import com.javarush.vrubleuski.entity.Rating;
import jakarta.persistence.AttributeConverter;

import java.nio.charset.StandardCharsets;

public class RatingConverter implements AttributeConverter<Enum<Rating>, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(Enum<Rating> attribute) {
        Rating rating = (Rating) attribute;
        byte[] data = switch (rating) {
            case G -> Rating.G.toString().getBytes(StandardCharsets.UTF_8);
            case PG -> Rating.PG.toString().getBytes(StandardCharsets.UTF_8);
            case PG13 -> Rating.PG13.toString().getBytes(StandardCharsets.UTF_8);
            case R -> Rating.R.toString().getBytes(StandardCharsets.UTF_8);
            case NC17 -> Rating.NC17.toString().getBytes(StandardCharsets.UTF_8);
        };
        return data;
    }

    @Override
    public Enum<Rating> convertToEntityAttribute(byte[] dbData) {
        String strRating = new String(dbData, StandardCharsets.UTF_8);
        Rating rating = switch (strRating) {
            case "G" -> Rating.G;
            case "PG" -> Rating.PG;
            case "PG-13" -> Rating.PG13;
            case "R" -> Rating.R;
            case "NC-17" -> Rating.NC17;
            default -> Rating.G;
        };
        return rating;
    }
}
