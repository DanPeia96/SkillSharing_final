package es.uji.ei1027.SkillSharing.Controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.Dao.UsuarioDao;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class UserValidator implements Validator {
    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {

        Usuario usuario= (Usuario) obj;


        if( usuario.getUserId().equals("")) {
            errors.rejectValue("userId", "obligatori", "Introduce el nombre de usuario");
        }
        if(usuario.getPassword().trim().equals(""))
            errors.rejectValue("password","obligatori","Introduce la contraseña");
/*
        if(usuario.estaActivo()==false){
            errors.rejectValue("userId", "usuario desactivado", "El usuario introducido está desactivado");
        }
  */  }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioDao usuarioDao;

    //llamada al metodo login
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return "/login";
    }

    //respuesta al login
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Usuario user, BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();

        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        Usuario userbbdd = usuarioDao.getTipo(user.getUserId());
        if (userbbdd == null) {
            bindingResult.rejectValue("userId", "obligatorio", "Usuario  incorrecto");
            return "login";
        }
        BasicPasswordEncryptor en = new BasicPasswordEncryptor();
        //userbbdd.setPassword(en.encryptPassword(user.getPassword()));


        if (!en.checkPassword(user.getPassword(),userbbdd.getPassword())) {
            bindingResult.rejectValue("password", "obligatorio", "Contraseña  incorrecta");
            return "login";
        }

        if (!userbbdd.estaActivo()) {
            bindingResult.rejectValue("userId", "Usuario Desactivado", "El usuario fue desactivado.");
            return "login";
        }

        //usuarioDao.setPassword(userbbdd.getUserId(),userbbdd.getPassword());

        session.setAttribute("user", userbbdd);
        session.setAttribute("tipo", userbbdd.esAdmin());
        if (userbbdd.esAdmin()) return "usuarios/sesionPromotor";
        else return "usuarios/sesionAlumno";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}