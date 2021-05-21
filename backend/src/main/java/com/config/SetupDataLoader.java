package com.config;

import com.model.*;
import com.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@Profile("!test")
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

      private boolean alreadySetup = false;

      @Autowired
      private BCryptPasswordEncoder passwordEncoder;

      @Autowired
      private RoleRepository roleRepository;

      @Autowired
      private UserRepository userRepository;

      @Autowired
      private BuildingRepository buildingRepository;

      @Autowired
      private RoomRepository roomRepository;

      @Autowired
      private SectionRepository sectionRepository;


      @Override
      @Transactional
      public void onApplicationEvent(ContextRefreshedEvent event) {

            if (alreadySetup)
                  return;
            Building building1 = createBuilding("Realfagsbygget", "Høgskoleringen 5", 5);
            Building building2 = createBuilding("Kjemibygget", "Høgskoleringen 16", 4);
            Building building3 = createBuilding("Gamle fysikk", "Høgskoleringen 12", 6);

            Room room1 = createRoom("R301", 3, building1);
            Room room2 = createRoom("R321", 3, building1);
            Room room3 = createRoom("R320", 3, building1);
            Room room4 = createRoom("R358", 3, building1);
            Room room5 = createRoom("R471", 4, building1);
            Room room6 = createRoom("R403", 4, building1);
            Room room7 = createRoom("R501", 5, building1);

            Room room8 = createRoom("KJ120", 1, building2);
            Room room9 = createRoom("KJ220", 2, building2);
            Room room10 = createRoom("KJ229", 2, building2);
            Room room11 = createRoom("KJ301", 3, building2);
            Room room12 = createRoom("KJ404", 4, building2);

            Room room13 = createRoom("FYN12", 1, building3);
            Room room14 = createRoom("FYN22", 2, building3);
            Room room15 = createRoom("FYS49", 4, building3);
            Room room16 = createRoom("FYS62", 6, building3);

            createSection("Hele rommet", 5, room1);
            createSection("Hele rommet", 10, room2);
            createSection("Hele rommet", 5, room3);
            createSection("Hele rommet", 9, room4);
            createSection("Hele rommet", 8, room5);
            createSection("Seksjon med HTC vive", 3, room6);
            createSection("Gruppebord 1", 6, room6);
            createSection("Gruppebord 2", 7, room6);
            createSection("Nord-østlige del av rommet", 6, room7);
            createSection("Gruppebord Y", 4, room7);
            createSection("Gruppebord X", 4, room7);
            createSection("Hele rommet", 8, room8);
            createSection("Hele rommet", 7, room9);
            createSection("Hele rommet", 7, room10);
            createSection("Hele rommet", 8, room11);
            createSection("Hele rommet", 4, room12);
            createSection("Seksjon med HTC vive", 3, room13);
            createSection("Gruppebord 1", 6, room13);
            createSection("Gruppebord 2", 7, room13);
            createSection("Gruppebord A", 4, room14);
            createSection("Gruppebord B", 6, room14);
            createSection("Gruppebord C", 6, room14);
            createSection("Nord-østlige del av rommet", 6, room15);
            createSection("Prosjektor med filmutstyr", 4, room15);
            createSection("Gruppebord X", 4, room15);
            createSection("Vestlige del av rommet", 15, room15);
            createSection("Øslige del av rommet", 14, room15);
            createSection("Gruppebord X", 10, room15);
            createSection("Hele rommet", 10, room16);


            Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN);
            Role userRole = createRoleIfNotFound(ERole.ROLE_USER);
            Role moderatorRole = createRoleIfNotFound(ERole.ROLE_MODERATOR);
            User admin = new User();
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.com");
            admin.setFirstName("admin");
            admin.setSurname("admin");
            admin.setRoles(Collections.singletonList(adminRole));
            userRepository.save(admin);

            User moderator = new User();
            moderator.setPassword(passwordEncoder.encode("moderator"));
            moderator.setEmail("moderator");
            moderator.setRoles(Collections.singletonList(moderatorRole));
            userRepository.save(moderator);

            User user = new User();
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user");
            user.setRoles(Collections.singletonList(userRole));
            userRepository.save(user);

            alreadySetup = true;
      }

      @Transactional
      Role createRoleIfNotFound(ERole name) {
            Optional<Role> existingRole = roleRepository.findByName(name);
            if (existingRole.isEmpty()) {
                  Role role = new Role();
                  role.setName(name);
                  return roleRepository.save(role);
            }
            return existingRole.get();
      }

      @Transactional
      Building createBuilding(String name, String address, int levels) {
            Building building = new Building();
            building.setName(name);
            building.setAddress(address);
            building.setLevels(levels);
            return buildingRepository.save(building);
      }

      @Transactional
      Room createRoom(String name, int level, Building building) {
            Room room = new Room();
            room.setName(name);
            room.setLevel(level);
            room.setBuilding(building);
            return roomRepository.save(room);
      }

      @Transactional
      Section createSection(String name, int capacity, Room room) {
            Section section = new Section();
            section.setName(name);
            section.setCapacity(capacity);
            section.setRoom(room);
            return sectionRepository.save(section);
      }
}
