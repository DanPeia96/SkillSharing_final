package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.UsuarioDao;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao){
        this.usuarioDao = usuarioDao;
    }

    @RequestMapping(value = "/sesionAlumno")
    public String loginAlumno(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if(usuario == null){
            return "redirect:/login";
        }
        //model.addAttribute("usuario", usuario);
        return "usuarios/sesionAlumno";
    }

    @RequestMapping(value = "/sesionPromotor")
    public String loginPromotor(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("user");
        if(usuario== null ){
            return "redirect:/login";
        }
       // model.addAttribute("usuario", usuario);
        return "usuarios/sesionPromotor";
    }


    @RequestMapping(value = "/verusuario/{codigo_usuario}", method = RequestMethod.GET)
    public String getUsuarioById(Model model, @PathVariable String codigo_usuario){
        System.out.println("-----------------------------------");
        model.addAttribute("usuario", usuarioDao.getUsuarioPorID(codigo_usuario));
        return "/usuarios/verUsuario";
    }

    @RequestMapping(value="/activar/{userId}", method = RequestMethod.GET)
    public String activaUsuario(Model model, @PathVariable String userId){
        model.addAttribute("usuario", usuarioDao.activateUsuario(userId));
        return "redirect:/usuarios/list";
    }

    @RequestMapping(value="/desactivar/{userId}", method = RequestMethod.GET)
    public String desactivaUsuario(Model model, @PathVariable String userId){
        model.addAttribute("usuario", usuarioDao.desactivateUsuario(userId));
        return "redirect:../list";
    }

    @RequestMapping(value="/redirectAddIncidencia/{alumnoId}", method = RequestMethod.GET)
    public String redirectAddIncidencia(Model model, @PathVariable String alumnoId){
        model.addAttribute("alumno", alumnoId);
        return "/incidencia/add/";
    }

    @RequestMapping("/list")
    public String listUsuarios(Model model){
        model.addAttribute("usuarios", usuarioDao.getUsuarios());
        return "usuarios/list";
    }
}