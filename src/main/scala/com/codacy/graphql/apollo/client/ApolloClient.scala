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
import scala.util.{Failure, Success}

object ApolloClient {
  def apply(url: URL, okHttpClient: OkHttpClient = new OkHttpClient.Builder().build()): ApolloClient =
    new ApolloClient(client(url, okHttpClient))

  def apply(apolloClient: apollo.ApolloClient): ApolloClient = new ApolloClient(apolloClient)

  private def client(url: URL, okHttpClient: OkHttpClient) = {
    apollo.ApolloClient.builder()
      .serverUrl(url.toString)
      .okHttpClient(okHttpClient)
      .addCustomTypeAdapter(CustomType.DATETIME, new DateTimeAdapter())
      .build()
  }
}

class ApolloClient(apolloClient: apollo.ApolloClient) {

  def execute[D <: Operation.Data, T, V <: Operation.Variables](query: Query[D, T, V]): Future[Response[T]] = {
    val promise = Promise[Response[T]]()
    apolloClient
      .query(query)
      .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
      .enqueue(new ApolloCall.Callback[T]() {
        override def onResponse(dataResponse: Response[T]): Unit = promise.complete(Success(dataResponse))

        override def onFailure(e: ApolloException): Unit = promise.complete(Failure(e))
      })
    promise.future
  }

}
