package utez.edu.mx.ruleman.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import utez.edu.mx.ruleman.model.Rol;
import utez.edu.mx.ruleman.model.Usuario;
import utez.edu.mx.ruleman.repository.RolRepository;
import utez.edu.mx.ruleman.repository.UsuarioRepository;

import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            //CREAR ROLES
            // Creamos el rol de Administrador si no existe
            Rol rolAdmin = rolRepository.findByNombre("ADMIN").orElseGet(() -> {
                Rol newRol = new Rol();
                newRol.setNombre("ADMIN");
                newRol.setDescripcion("Acceso total al sistema");
                return rolRepository.save(newRol);
            });

            // Creamos el rol de Mecánico si no existe
            Rol rolMecanico = rolRepository.findByNombre("MECANICO").orElseGet(() -> {
                Rol newRol = new Rol();
                newRol.setNombre("MECANICO");
                newRol.setDescripcion("Acceso a la gestión de servicios");
                return rolRepository.save(newRol);
            });

            // Creamos el rol de Cliente si no existe
            Rol rolCliente = rolRepository.findByNombre("CLIENTE").orElseGet(() -> {
                Rol newRol = new Rol();
                newRol.setNombre("CLIENTE");
                newRol.setDescripcion("Acceso para registrar y ver servicios");
                return rolRepository.save(newRol);
            });

            //CREAR USUARIOS DE PRUEBA
            Optional<Usuario> optionalAdmin = usuarioRepository.findFirstByCorreo("20233tn083@utez.edu.mx");
            if (!optionalAdmin.isPresent()) {
                Usuario userAdmin = new Usuario();
                userAdmin.setApellidos("Rodriguez");
                userAdmin.setNombre("Rocio");
                userAdmin.setCorreo("20233tn105@utez.edu.mx");
                userAdmin.setContrasena(passwordEncoder.encode("12345"));
                userAdmin.setNumeroTelefono("123456789");
                userAdmin.setEstatus(true);
                userAdmin.setRol(rolAdmin);
                usuarioRepository.saveAndFlush(userAdmin);
            }


            Optional<Usuario> optionalMechanic = usuarioRepository.findFirstByCorreo("20233tn093@utez.edu.mx");
            if (!optionalMechanic.isPresent()) {
               Usuario userMechanic = new Usuario();
                userMechanic.setApellidos("Chávez");
                userMechanic.setNombre("Uxue");
                userMechanic.setCorreo("20233tn093@utez.edu.mx");
                userMechanic.setContrasena(passwordEncoder.encode("12345"));
                userMechanic.setNumeroTelefono("1234567891");
                userMechanic.setEstatus(true);
                userMechanic.setRol(rolMecanico);
                usuarioRepository.saveAndFlush(userMechanic);
            }


            Optional<Usuario> optionalClient = usuarioRepository.findFirstByCorreo("20233tn083@utez.edu.mx");
            if (!optionalClient.isPresent()) {
               Usuario userClient = new Usuario();
                userClient.setApellidos("Delgado");
                userClient.setNombre("Alexa");
                userClient.setCorreo("20233tn083@utez.edu.mx");
                userClient.setContrasena(passwordEncoder.encode("12345"));
                userClient.setNumeroTelefono("1234567892");
                userClient.setEstatus(true);
                userClient.setRol(rolCliente);
                usuarioRepository.saveAndFlush(userClient);
            }

        };
    }
}
