package com.sistema_bancarioSpring.SistemaBancarioSpring;

import com.sistema_bancarioSpring.SistemaBancarioSpring.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SistemaBancarioSpringApplication
        implements CommandLineRunner {

    private final MenuService MenuService;

    public static void main(String[] args) {
        SpringApplication.run(
                SistemaBancarioSpringApplication.class,
                args);
    }

    @Override
    public void run(String... args) {
        MenuService.iniciar();
    }
}
