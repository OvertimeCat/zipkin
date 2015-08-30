/*
 * Copyright 2012 Twitter Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.twitter.zipkin.storage

import com.twitter.algebird.Monoid
import com.twitter.util.{Future, Time}
import com.twitter.zipkin.common.Dependencies

/**
 * Storage and retrieval interface for aggregates that may be computed offline and reloaded into
 * online storage
 */
abstract class Aggregates extends java.io.Closeable {

  def getDependencies(startDate: Option[Time], endDate: Option[Time]=None): Future[Dependencies]
  def storeDependencies(dependencies: Dependencies): Future[Unit]

  @deprecated("This didn't have UI support", "1.3.0")
  def getTopAnnotations(serviceName: String): Future[Seq[String]] = {
    Future.exception(new UnsupportedOperationException("This is no longer used"))
  }
  @deprecated("This didn't have UI support", "1.3.0")
  def getTopKeyValueAnnotations(serviceName: String): Future[Seq[String]] = {
    Future.exception(new UnsupportedOperationException("This is no longer used"))
  }
  @deprecated("This didn't have UI support", "1.3.0")
  def storeTopAnnotations(serviceName: String, a: Seq[String]): Future[Unit] = {
    Future.exception(new UnsupportedOperationException("This is no longer used"))
  }
  @deprecated("This didn't have UI support", "1.3.0")
  def storeTopKeyValueAnnotations(serviceName: String, a: Seq[String]): Future[Unit] = {
    Future.exception(new UnsupportedOperationException("This is no longer used"))
  }
}

class NullAggregates extends Aggregates {

  def close() {}

  def getDependencies(startDate: Option[Time], endDate: Option[Time] = None) = Future(Monoid.zero[Dependencies])
  def storeDependencies(dependencies: Dependencies): Future[Unit]                    = Future.Unit
}
