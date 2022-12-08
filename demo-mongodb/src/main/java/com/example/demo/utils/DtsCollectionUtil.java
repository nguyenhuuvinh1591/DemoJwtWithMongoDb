package com.example.demo.utils;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;

public class DtsCollectionUtil {

    @SuppressWarnings("rawtypes")
    public static final Collection EMPTY_COLLECTION = CollectionUtils.EMPTY_COLLECTION;

    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static int size(Collection<?> collection) {
        return CollectionUtils.size(collection);
    }
}
