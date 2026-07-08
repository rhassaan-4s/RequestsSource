package com._4s_.clients.web.action;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._4s_.clients.web.util.Tenant;

@RestController
@RequestMapping("/style")
public class StyleController {
    @GetMapping(value = "tenantStyle.css", produces = {"text/css"})
    public String getTenantStyle(@Tenant String tenant) {
        // Custom theme should be stored in the DB. Currently very hard-coded...
        if (tenant == null) {
            return "body { background-color: #fcd2d2; }";
        } else if (tenant.equals("tenant1")) {
            return "body { background-color: #ebebfc; }";
        } else if (tenant.equals("tenant2")) {
            return "body { background-color: #edfceb; }";
        } else {
            return "";
        }
    }
}
