package mx.ipn.escom.Recomendaciones.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import mx.ipn.escom.Recomendaciones.auth.entity.Usuario;
import mx.ipn.escom.Recomendaciones.auth.entity.Rol;
import mx.ipn.escom.Recomendaciones.auth.repository.UsuarioRepository;
import mx.ipn.escom.Recomendaciones.auth.repository.RolRepository;

import java.util.Optional; // Importa la clase Optional

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/user/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario, @RequestParam String rol) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);

        Optional<Rol> optionalRole = rolRepository.findByNombre(rol);
        Rol role;
        if (optionalRole.isPresent()) {
            role = optionalRole.get();
        } else {
            role = new Rol();
            role.setNombre(rol);
            rolRepository.save(role);
        }

        usuario.getRoles().add(role);
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }
}