/*
 * Copyright (c) Microsoft Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.playwright;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * {@code Download} objects are dispatched by page via the {@link Page#onDownload Page.onDownload()} event.
 *
 * <p> All the downloaded files belonging to the browser context are deleted when the browser context is closed.
 *
 * <p> Download event is emitted once the download starts. Download path becomes available once download completes:
 * <pre>{@code
 * // wait for download to start
 * Download download  = page.waitForDownload(() -> page.locator("a").click());
 * // wait for download to complete
 * Path path = download.path();
 * }</pre>
 * <pre>{@code
 * // wait for download to start
 * Download download = page.waitForDownload(() -> {
 *   page.locator("a").click();
 * });
 * // wait for download to complete
 * Path path = download.path();
 * }</pre>
 */
public interface Download {
  /**
   * Cancels a download. Will not fail if the download is already finished or canceled. Upon successful cancellations,
   * {@code download.failure()} would resolve to {@code "canceled"}.
   */
  void cancel();
  /**
   * Returns readable stream for current download or {@code null} if download failed.
   */
  InputStream createReadStream();
  /**
   * Deletes the downloaded file. Will wait for the download to finish if necessary.
   */
  void delete();
  /**
   * Returns download error if any. Will wait for the download to finish if necessary.
   */
  String failure();
  /**
   * Get the page that the download belongs to.
   */
  Page page();
  /**
   * Returns path to the downloaded file in case of successful download. The method will wait for the download to finish if
   * necessary. The method throws when connected remotely.
   *
   * <p> Note that the download's file name is a random GUID, use {@link Download#suggestedFilename Download.suggestedFilename()}
   * to get suggested file name.
   */
  Path path();
  /**
   * Copy the download to a user-specified path. It is safe to call this method while the download is still in progress. Will
   * wait for the download to finish if necessary.
   *
   * @param path Path where the download should be copied.
   */
  void saveAs(Path path);
  /**
   * Returns suggested filename for this download. It is typically computed by the browser from the <a
   * href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">{@code Content-Disposition}</a> response
   * header or the {@code download} attribute. See the spec on <a
   * href="https://html.spec.whatwg.org/#downloading-resources">whatwg</a>. Different browsers can use different logic for
   * computing it.
   */
  String suggestedFilename();
  /**
   * Returns downloaded url.
   */
  String url();
}

