// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.locks.implementation;

import com.azure.resourcemanager.locks.fluent.models.OperationInner;
import com.azure.resourcemanager.locks.models.Operation;
import com.azure.resourcemanager.locks.models.OperationDisplay;

public final class OperationImpl implements Operation {
    private OperationInner innerObject;

    private final com.azure.resourcemanager.locks.ManagementLockManager serviceManager;

    OperationImpl(OperationInner innerObject, com.azure.resourcemanager.locks.ManagementLockManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
    }

    public String name() {
        return this.innerModel().name();
    }

    public OperationDisplay display() {
        return this.innerModel().display();
    }

    public OperationInner innerModel() {
        return this.innerObject;
    }

    private com.azure.resourcemanager.locks.ManagementLockManager manager() {
        return this.serviceManager;
    }
}
