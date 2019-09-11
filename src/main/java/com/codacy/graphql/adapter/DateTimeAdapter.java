package com.codacy.graphql.adapter;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class DateTimeAdapter implements CustomTypeAdapter<Instant> {

    @Override
    public Instant decode(@NotNull CustomTypeValue value) {
        String valueStr = (String) value.value;
        return Instant.parse(valueStr);
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull Instant value) {
        return CustomTypeValue.fromRawValue(
                DateTimeFormatter.ISO_DATE_TIME.format(value)
        );
    }

}

