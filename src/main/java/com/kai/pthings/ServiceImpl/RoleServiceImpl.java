package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
import com.kai.pthings.entity.Role;
import com.kai.pthings.repository.RoleRepository;
import com.kai.pthings.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void generateRole() {
        Role role_user = new Role();
        Role role_admin = new Role();
        Role role_mod = new Role();
        role_user.setId(UUID.randomUUID().toString());
        role_admin.setId(UUID.randomUUID().toString());
        role_mod.setId(UUID.randomUUID().toString());
        role_user.setName(Snippets.ROLE_USER);
        role_admin.setName(Snippets.ROLE_ADMIN);
        role_mod.setName(Snippets.ROLE_MOD);
        roleRepository.save(role_user);
        roleRepository.save(role_admin);
        roleRepository.save(role_mod);
    }
}
