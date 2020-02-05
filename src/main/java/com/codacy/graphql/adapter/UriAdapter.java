package com.codacy.graphql.adapter;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import org.jetbrains.annotations.NotNull;

public class UriAdapter implements CustomTypeAdapter<String> {

    @Override
    public String decode(@NotNull CustomTypeValue value) {
        return (String) value.value;
    }

    @NotNull
    @Override
    public CustomTypeValue<String> encode(@NotNull String value) {
        return new CustomTypeValue.GraphQLString(value);
    }

}