package com.kai.pthings.config;


import com.kai.pthings.ServiceImpl.CategoryServiceImpl;
import com.kai.pthings.ServiceImpl.TagServiceImpl;
import com.kai.pthings.entity.Category;
import com.kai.pthings.entity.Role;
import com.kai.pthings.entity.Tag;
import com.kai.pthings.repository.CategoryRepository;
import com.kai.pthings.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Database implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    TagServiceImpl tagService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
            createRoleStartingUp("ROLE_ADMIN");
            createRoleStartingUp("ROLE_USER");
            createRoleStartingUp("ROLE_MOD");

            createTagStartingUp("Life","#f74366");
            createTagStartingUp("Family","#66cccc");
            createTagStartingUp("Stress","#c14f30");
            createTagStartingUp("World","#D32500");

            createCategoryStartingUp("Book","#00CDD3");
            createCategoryStartingUp("Confess","#0B763C");
            createCategoryStartingUp("Story","#8858FF");

    }

    private void createRoleStartingUp (String name){
        Role role = roleRepository.findByName(name);
        if (role == null){
            role = new Role();
            role.setId(UUID.randomUUID().toString());
            role.setName(name);
            roleRepository.save(role);
        }
    }

    private void createCategoryStartingUp (String name, String color){
        Category category = categoryService.findByMetaName(Snippets.convertMeta(name));
        if(category == null){
            category = new Category();
            category.setName(name);
            category.setColor(color);
            category.setDisplay(true);
            category.setId(UUID.randomUUID().toString());
            categoryService.add_new(category);
        }
    }

    private void createTagStartingUp (String name, String color){
        Tag tag = tagService.findByMetaName(Snippets.convertMeta(name));
        if(tag == null){
            tag = new Tag();
            tag.setName(name);
            tag.setDisplay(true);
            tag.setId(UUID.randomUUID().toString());
            tag.setColor(color);
            tagService.add_new(tag);
        }
    }
}
