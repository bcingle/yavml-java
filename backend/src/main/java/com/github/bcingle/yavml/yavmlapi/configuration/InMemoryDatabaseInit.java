package com.github.bcingle.yavml.yavmlapi.configuration;

import com.github.bcingle.yavml.yavmlapi.model.Fleet;
import com.github.bcingle.yavml.yavmlapi.model.Vehicle;
import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import com.github.bcingle.yavml.yavmlapi.repository.FleetRepository;
import com.github.bcingle.yavml.yavmlapi.repository.UserRepository;
import com.github.bcingle.yavml.yavmlapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Sets up several users and other data for initial application usage when
 * started on a development server.
 */
@Component
@Profile("development")
public class InMemoryDatabaseInit implements ApplicationRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FleetRepository fleetRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        YavmlUser me = new YavmlUser();
        me.setUsername("bcingle");
        me.setEmail("benjamin.ingle@gmail.com");
        me.setLocale(Locale.US.toString());
        me.setPassword(new BCryptPasswordEncoder().encode("jesus789"));
        me = userRepo.save(me);

        Fleet fleet = new Fleet();
        fleet.setOwner(me);
        fleet.setName("My Fleet");
        fleet = fleetRepo.save(fleet);

        Vehicle vehicle = new Vehicle();
        vehicle.setFleet(fleet);
        vehicle.setMake("Ford");
        vehicle.setModel("Mustang");
        vehicle.setName("The Stang");
        vehicle.setTrim("GT");
        vehicle.setVin("ABC1234567890");
        vehicle.getOptions().add("Leather Interior");
        vehicle.getOptions().add("Premium Sound");
        vehicleRepo.save(vehicle);

        YavmlUser other = new YavmlUser();
        other.setUsername("someoneElse");
        other.setPassword(new BCryptPasswordEncoder().encode("other"));
        other.setEmail("someome@else.com");
        userRepo.save(other);

        YavmlUser admin = new YavmlUser();
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
        admin.setEmail("admin@yavml.com");
        userRepo.save(admin);
    }
}
