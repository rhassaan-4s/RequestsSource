package com._4s_.clients.web.util;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com._4s_.clients.dao.TenantRepository;
import com._4s_.clients.model.Tenant;

public class TenantFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(TenantFilter.class.getName());

    @Autowired
    private TenantRepository tenantRepository;

//    public TenantFilter(TenantRepository tenantRepository) {
//        this.tenantRepository = tenantRepository;
//    }
    

    public TenantRepository getTenantRepository() {
		return tenantRepository;
	}

	public void setTenantRepository(TenantRepository tenantRepository) {
		logger.debug("tenantRepository " + tenantRepository);
		this.tenantRepository = tenantRepository;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
    	logger.debug("doFilterInternal ");
        String tenant = getTenant(request);
        logger.debug("doFilterInternal tenant " + tenant);
        Long tenantId = tenantRepository.findBySlug(tenant).map(Tenant::getId).orElse(null);
        if (tenant != null && tenantId == null) {
            // Attempted access to non-existing tenant
            response.setStatus(NOT_FOUND.value());
            return;
        }
        LOGGER.info("Setting tenant: " + tenant + " (domain " + request.getServerName() + ")");
        LOGGER.info("Setting tenant ID: " + tenantId);
        TenantContext.setCurrentTenant(tenant);
        TenantContext.setCurrentTenantId(tenantId);
        chain.doFilter(request, response);
    }

    public TenantFilter() {
		// TODO Auto-generated constructor stub
    	logger.debug("#####initializing TENANT FILTER####");
    	logger.debug("repository " + tenantRepository);
	}

	@Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/webjars/")
                || request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().endsWith(".ico");
    }

    private String getTenant(HttpServletRequest request) {
        String domain = request.getServerName();
        int dotIndex = domain.indexOf(".");

        String tenant = null;
        if (dotIndex != -1) {
            tenant = domain.substring(0, dotIndex);
            logger.debug("tenant " + tenant);
        }

        return tenant;
    }
}
