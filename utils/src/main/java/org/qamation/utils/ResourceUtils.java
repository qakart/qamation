package org.qamation.utils;

import java.net.URL;

/**
 * Created by Pavel.Gouchtchine on 12/09/2016.
 */
public class ResourceUtils {
    public static URL getURLForResouce(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(resourceName);
    }
}
