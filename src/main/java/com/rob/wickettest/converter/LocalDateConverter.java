package com.rob.wickettest.converter;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class LocalDateConverter implements IConverter<LocalDate>
{
    public static final String FORMAT = "MM/dd/yyyy";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(FORMAT);

    @Override
    public LocalDate convertToObject(String value, Locale locale) throws ConversionException
    {
        try
        {
            return DATE_TIME_FORMATTER.parseLocalDate(value);
        }
        catch (IllegalArgumentException e)
        {
            throw new ConversionException("Invalid date format");
        }
    }

    @Override
    public String convertToString(LocalDate value, Locale locale)
    {
        return value.toString(FORMAT);
    }
}
