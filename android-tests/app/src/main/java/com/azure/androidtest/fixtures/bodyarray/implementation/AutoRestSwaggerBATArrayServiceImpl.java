package com.azure.androidtest.fixtures.bodyarray.implementation;

import com.azure.android.core.http.ServiceClient;
import com.azure.android.core.http.responsepaging.AsyncPagedDataRetriever;
import com.azure.android.core.http.responsepaging.PagedDataResponseRetriever;
import com.azure.android.core.internal.util.serializer.SerializerAdapter;
import com.azure.android.core.internal.util.serializer.SerializerFormat;
import com.azure.android.core.util.paging.PagedDataRetriever;

/**
 * Initializes a new instance of the AutoRestSwaggerBATArrayService type.
 */
public final class AutoRestSwaggerBATArrayServiceImpl {
    /**
     * The serializer.
     */
    final SerializerAdapter serializerAdapter = SerializerAdapter.createDefault();

    /**
     * The Azure Core generic ServiceClient to setup interceptors and produce retrofit proxy.
     */
    private ServiceClient serviceClient;

    /**
     * Gets The Azure Core generic ServiceClient to setup interceptors and produce retrofit proxy.
     * 
     * @return the serviceClient value.
     */
    public ServiceClient getServiceClient() {
        return this.serviceClient;
    }

    /**
     * server parameter.
     */
    private final String host;

    /**
     * Gets server parameter.
     * 
     * @return the host value.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * The ArraysImpl object to access its operations.
     */
    private final ArraysImpl arrays;

    /**
     * Gets the ArraysImpl object to access its operations.
     * 
     * @return the ArraysImpl object.
     */
    public ArraysImpl getArrays() {
        return this.arrays;
    }

    /**
     * Initializes an instance of AutoRestSwaggerBATArrayService client.
     * 
     * @param serviceClient The Azure Core generic ServiceClient to setup interceptors and produce retrofit proxy.
     * @param host server parameter.
     */
    public AutoRestSwaggerBATArrayServiceImpl(ServiceClient serviceClient, String host) {
        this.serviceClient = serviceClient;
        this.host = host;
        this.arrays = new ArraysImpl(this);
    }

    String readAsString(okhttp3.ResponseBody body) {
        if (body == null) {
            return "";
        }
        try {
            return new String(body.bytes());
        } catch(java.io.IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            body.close();
        }
    }

    <T> T deserializeContent(okhttp3.Headers headers, okhttp3.ResponseBody body, java.lang.reflect.Type type) {
        if (type.equals(byte[].class)) {
            try {
                if (body.contentLength() == 0) {
                    return null;
                }
                return (T) body.bytes();
            } catch(java.io.IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        final String str = readAsString(body);
        try {
            final String mimeContentType = headers.get(CONTENT_TYPE);
            return this.serializerAdapter.deserialize(str, type, resolveSerializerFormat(mimeContentType));
        } catch(java.io.IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    <T> T deserializeHeaders(okhttp3.Headers headers, java.lang.reflect.Type type) {
        try {
            return this.serializerAdapter.deserialize(headers, type);
        } catch(java.io.IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    <T> retrofit2.Response<T> executeRetrofitCall(retrofit2.Call<T> call) {
        try {
            return call.execute();
        } catch(java.io.IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    java.lang.reflect.ParameterizedType createParameterizedType(Class<?> rawClass, java.lang.reflect.Type... genericTypes) {
        return new java.lang.reflect.ParameterizedType() {
            @Override
            public java.lang.reflect.Type[] getActualTypeArguments() {
                return genericTypes;
            }
            @Override
            public java.lang.reflect.Type getRawType() {
                return rawClass;
            }
            @Override
            public java.lang.reflect.Type getOwnerType() {
                return null;
            }
        };
    }

    private static final String CONTENT_TYPE = "Content-Type";

    private static final java.util.Map<String, SerializerFormat> SUPPORTED_MIME_TYPES = new java.util.TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private static final java.util.TreeMap<String, SerializerFormat> SUPPORTED_SUFFIXES = new java.util.TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private static final SerializerFormat DEFAULT_ENCODING = SerializerFormat.JSON;
    static {
        SUPPORTED_MIME_TYPES.put("text/xml", SerializerFormat.XML);
        SUPPORTED_MIME_TYPES.put("application/xml", SerializerFormat.XML);
        SUPPORTED_MIME_TYPES.put("application/json", SerializerFormat.JSON);
        SUPPORTED_SUFFIXES.put("xml", SerializerFormat.XML);
        SUPPORTED_SUFFIXES.put("json", SerializerFormat.JSON);
    }

    SerializerFormat resolveSerializerFormat(String mimeContentType) {
        if (mimeContentType == null || mimeContentType.isEmpty()) {
            return DEFAULT_ENCODING;
        }
        final String[] parts = mimeContentType.split(";");
        final SerializerFormat encoding = SUPPORTED_MIME_TYPES.get(parts[0]);
        if (encoding != null) {
            return encoding;
        }
        final String[] mimeTypeParts = parts[0].split("/");
        if (mimeTypeParts.length != 2) {
            return DEFAULT_ENCODING;
        }
        final String subtype = mimeTypeParts[1];
        final int lastIndex = subtype.lastIndexOf("+");
        if (lastIndex == -1) {
            return DEFAULT_ENCODING;
        }
        final String mimeTypeSuffix = subtype.substring(lastIndex + 1);
        final SerializerFormat serializerEncoding = SUPPORTED_SUFFIXES.get(mimeTypeSuffix);
        if (serializerEncoding != null) {
            return serializerEncoding;
        }
        return DEFAULT_ENCODING;
    }
}