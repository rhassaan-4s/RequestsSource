package com._4s_.common.dao;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaTenantResolver
        implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {

        String tenant = TenantContext.getTenant();

        if (tenant == null) {
            tenant = TenantContext.getDefaultTenant();
        }

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
//public class SchemaTenantResolver implements CurrentTenantIdentifierResolver {
//
//	@Override
//	public String resolveCurrentTenantIdentifier() {
//		String tenant = TenantContext.getTenant();
//		return tenant != null ? tenant : "fours_payroll";
//	}
//
//	@Override
//	public boolean validateExistingCurrentSessions() {
//		return true;
//	}
//}