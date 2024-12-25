package com.getjavajob.training.timashovy.socialnetwork.service.interfaces;

import org.springframework.security.access.prepost.PreAuthorize;

public interface AdminService {

    @PreAuthorize("hasAuthority('ADMIN')")
    void makeAdmin(Long accountId);

}
