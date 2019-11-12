// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.header;

import com.azure.core.http.HttpPipeline;

/**
 * The interface for AutoRestSwaggerBATHeaderService class.
 */
public interface AutoRestSwaggerBATHeaderService {
    /**
     * Gets The HTTP pipeline to send requests through.
     *
     * @return the httpPipeline value.
     */
    HttpPipeline getHttpPipeline();

    /**
     * Gets the Headers object to access its operations.
     *
     * @return the Headers object.
     */
    Headers headers();
}