package com.github.bcingle.yavml.yavmlapi;

import com.github.bcingle.yavml.yavmlapi.model.Fleet;
import com.github.bcingle.yavml.yavmlapi.model.Vehicle;
import com.github.bcingle.yavml.yavmlapi.model.YavmlUser;
import com.github.bcingle.yavml.yavmlapi.repository.FleetRepository;
import com.github.bcingle.yavml.yavmlapi.repository.UserRepository;
import com.github.bcingle.yavml.yavmlapi.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@SpringBootApplication
public class YavmlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YavmlApiApplication.class, args);
	}

}
