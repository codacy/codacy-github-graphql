package com.codacy.graphql.adapter;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter implements CustomTypeAdapter<LocalDate> {

    @Override
    public LocalDate decode(@NotNull CustomTypeValue value) {
        String valueStr = (String) value.value;
        return LocalDate.parse(valueStr);
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull LocalDate value) {
        return CustomTypeValue.fromRawValue(
                DateTimeFormatter.ISO_DATE.format(value)
        );
    }

}
