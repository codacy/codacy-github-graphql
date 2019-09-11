package com.codacy.graphql.apollo.client

import java.net.URL

import com.apollographql.apollo
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.{Operation, Query, Response}
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.codacy.graphql.adapter.DateTimeAdapter
import com.github.api.v4.`type`.CustomType
import okhttp3.OkHttpClient

import scala.concurrent.{Future, Promise}

object ScalaApolloClient {
  def apply(url: URL, okHttpClient: OkHttpClient = new OkHttpClient.Builder().build()): ScalaApolloClient =
    new ScalaApolloClient(client(url, okHttpClient))

  def apply(apolloClient: apollo.ApolloClient): ScalaApolloClient = new ScalaApolloClient(apolloClient)

  private def client(url: URL, okHttpClient: OkHttpClient) = {
    apollo.ApolloClient.builder()
      .serverUrl(url.toString)
      .okHttpClient(okHttpClient)
      .addCustomTypeAdapter(CustomType.DATETIME, new DateTimeAdapter())
      .build()
  }
}

class ScalaApolloClient(val underlying: apollo.ApolloClient) {

  def execute[D <: Operation.Data, T, V <: Operation.Variables](query: Query[D, T, V]): Future[Response[T]] = {
    val promise = Promise[Response[T]]()
    underlying
      .query(query)
      .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
      .enqueue(new ApolloCall.Callback[T]() {
        override def onResponse(dataResponse: Response[T]): Unit = promise.trySuccess(dataResponse)

        override def onFailure(e: ApolloException): Unit = promise.tryFailure(e)
      })
    promise.future
  }

}
