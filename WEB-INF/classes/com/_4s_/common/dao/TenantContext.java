package com._4s_.common.dao;

import java.io.IOException;
import java.util.Properties;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    private static final String DEFAULT = loadDefaultTenant();

    public static void setTenant(String tenant) {
        CURRENT.set(tenant);
    }

    public static String getTenant() {
        return CURRENT.get();   // IMPORTANT: DO NOT fallback here
    }

    public static String getDefaultTenant() {
        return DEFAULT;
    }

    public static void clear() {
        CURRENT.remove();
    }

    private static String loadDefaultTenant() {
        Properties props = new Properties();
        try {
            props.load(
                TenantContext.class.getResourceAsStream(
                    "/hibernate.properties"
                )
            );

            String schema =
                props.getProperty("hibernate.defaultSchema");

            if (schema != null && !schema.isEmpty()) {
                return schema;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fours_payroll";
    }
}

//public class TenantContext {
//    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();
//    private static final String DEFAULT = loadDefaultTenant();
//
//    public static void setTenant(String tenant) {
//        CURRENT.set(tenant);
//    }
//
//    public static String getTenant() {
//        return CURRENT.get() != null ? CURRENT.get() : DEFAULT;
//    }
//
//    public static void clear() {
//        CURRENT.remove();
//    }
//
//    /**
//     * Load the default tenant from hibernate.properties
//     */
//    private static String loadDefaultTenant() {
//        Properties props = new Properties();
//        try {
//            props.load(TenantContext.class.getResourceAsStream("/hibernate.properties"));
//            String defaultSchema = props.getProperty("hibernate.defaultSchema");
//            if (defaultSchema != null && !defaultSchema.isEmpty()) {
//                return defaultSchema;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // fallback if property missing
//        return "fours_payroll";
//    }
//}