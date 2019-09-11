package com.codacy.graphql.adapter;

import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class URLAdapter implements CustomTypeAdapter<URL> {

    @Override
    public URL decode(@NotNull CustomTypeValue value) {
        String valueStr = (String) value.value;
        try {
            return new URL(valueStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @NotNull
    @Override
    public CustomTypeValue encode(@NotNull URL value) {
        return CustomTypeValue.fromRawValue(
                value.toString()
        );
    }

}
