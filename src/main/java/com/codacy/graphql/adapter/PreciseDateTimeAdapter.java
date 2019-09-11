package com.codacy.graphql.adapter;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class PreciseDateTimeAdapter implements CustomTypeAdapter<Instant> {

    @Override
    public Instant decode(@NotNull CustomTypeValue value) {
        Long valueLong = (Long) value.value;
        return Instant.ofEpochMilli(valueLong);
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull Instant value) {
        return CustomTypeValue.fromRawValue(
                value.toEpochMilli()
        );
    }

}
