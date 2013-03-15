/*
 * Copyright 2012 Shared Learning Collaborative, LLC
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

package org.slc.sli.shtick;

/**
 * @author jstokes
 */
public final class StatusCodeException extends Exception {
    private static final long serialVersionUID = 1L;
    
    private int statusCode;
    
    public StatusCodeException(final int statusCode) {
        this(statusCode, null);
    }

    public StatusCodeException(Throwable cause, final int statusCode) {
        this(cause, statusCode, null);
    }
    
    public StatusCodeException(final int statusCode, final String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public StatusCodeException(Throwable cause, final int statusCode, final String message) {
        super(message, cause);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
}