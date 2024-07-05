package org.softwaregr5.dantulootravel.dantulootravel.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.softwaregr5.dantulootravel.dantulootravel.domain.DTO.EmailDTO.EmailDTO;
import org.softwaregr5.dantulootravel.dantulootravel.domain.DTO.usuarioDTO.DatosRegistroUsuario;
import org.softwaregr5.dantulootravel.dantulootravel.domain.entity.Usuarios.Conductor;
import org.softwaregr5.dantulootravel.dantulootravel.domain.entity.Usuarios.Pasajero;
import org.softwaregr5.dantulootravel.dantulootravel.domain.entity.Usuarios.Rol;
import org.softwaregr5.dantulootravel.dantulootravel.domain.entity.Usuarios.Usuario;
import org.softwaregr5.dantulootravel.dantulootravel.domain.mappers.LoginRequest;
import org.softwaregr5.dantulootravel.dantulootravel.domain.mappers.LoginResponse;
import org.softwaregr5.dantulootravel.dantulootravel.repos.repository.Usuarios.ConductorRepository;
import org.softwaregr5.dantulootravel.dantulootravel.repos.repository.Usuarios.UsuarioRepository;
import org.softwaregr5.dantulootravel.dantulootravel.repos.security.EncryptionUtil;
import org.softwaregr5.dantulootravel.dantulootravel.repos.security.JwtTokenUtil;
import org.softwaregr5.dantulootravel.dantulootravel.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    @Autowired
    private ConductorRepository conductorRepository;


    @Override
    public LoginResponse loginusuario(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario user = usuarioRepository.findByCorreo(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: "+ loginRequest.getEmail())) ;

        String token = jwtTokenUtil.generateToken(user);
        String encryptedToken = EncryptionUtil.encrypt(token);

        Rol role = user.getRole();


        return new LoginResponse(encryptedToken, role);
    }


    @Override
    public String guardarUsuario(DatosRegistroUsuario datos){

        if (datos.role() != Rol.CONDUCTOR && datos.role() != Rol.PASAJERO){
            throw new IllegalArgumentException("Rol no valido: " + datos.role());
        }

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(datos.correo());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está en uso: " + datos.correo());
        }




        Optional<Usuario> dniexistente = usuarioRepository.findByDni(datos.dni());
        if (dniexistente.isPresent()) {
            throw new IllegalArgumentException("El DNI ya está registrado: " + datos.dni());
        }

        if(datos.contrasena().length() < 8){
            throw new IllegalArgumentException("Contraseña invalida: " + datos.contrasena());
        }

        if (datos.dni() == null || !datos.dni().toString().matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe contener exactamente 8 dígitos numéricos.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(datos.nombre());
        usuario.setCorreo(datos.correo());
        usuario.setDni(datos.dni());
        usuario.setSexo(datos.sexo());
        usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));
        usuario.setTelefono(datos.telefono());
        usuario.setFecha_nacimiento(datos.fecha_nacimiento());
        usuario.setRole(datos.role());

        // Si el rol es Conductor, también crea un conductor asociado
        if (datos.role() == Rol.CONDUCTOR) {
            Conductor conductor = new Conductor();
            conductor.setCalificacionPromedio(1.0);
            conductor.setUsuario(usuario);
            usuario.setConductor(conductor);
        }
        if (datos.role() == Rol.PASAJERO) {
            Pasajero pasajero = new Pasajero();
            pasajero.setUsuario(usuario);
            usuario.setPasajero(pasajero);
        }

        usuarioRepository.save(usuario);

        return "Usuario guardado con éxito: " + usuario.getNombre();

    }


    @Override
    public void olvideContrasena(EmailDTO email) {

        String token = jwtTokenUtil.generateToken(email.getDestinatario());
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        Context context = new Context();
        context.setVariable("mensaje", resetUrl);

        String html = templateEngine.process("email", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email.getDestinatario());
            helper.setSubject("Password Reset Request");
            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }

    @Override
    public void cambiarContrasena(String email, String newPassword){
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + email));

        usuario.setContrasena(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
    }


    @Override
    public Usuario buscarusuario(Long idusuario){

        Usuario usuario = usuarioRepository.findById(idusuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idusuario));

        return usuario;
    }

    @Override
    public Conductor buscarconductor(Long idConductor) {
        return conductorRepository.findById(idConductor).orElseThrow(() -> new IllegalArgumentException("Conductor no encontrado"));
    }

    @Override
    public Conductor buscarconductorPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return conductorRepository.findByUsuario(usuario).orElseThrow(() -> new IllegalArgumentException("Conductor no encontrado para el usuario con ID: " + usuarioId));
    }

    @Override
    public String recuperarNombre(String email){
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new IllegalArgumentException("{\"success\": false, \"message\": \"Usuario no encontrado\"}"));

        String nombre = usuario.getNombre();

        return "{\"success\": true, \"nombre\": \""+nombre+"\"}";

    }

}
