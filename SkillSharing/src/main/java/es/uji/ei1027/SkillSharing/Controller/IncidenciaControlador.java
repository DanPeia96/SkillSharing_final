package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.IncidenciaDao;
import es.uji.ei1027.SkillSharing.Dao.UsuarioDao;
import es.uji.ei1027.SkillSharing.Model.Incidencia;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;


@Controller
@RequestMapping("/incidencia")
public class IncidenciaControlador {
    private IncidenciaDao incidenciaDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setIncidenciaDao(IncidenciaDao iDao, UsuarioDao usuarioDao){
        this.incidenciaDao = iDao;
        this.usuarioDao=usuarioDao;
    }

    @RequestMapping("/list")
    public String listIncidencias(Model model){
        model.addAttribute("incidencias", incidenciaDao.getIncidencias());
        return "incidencia/list";
    }

    @RequestMapping(value = "/add/{id_alumno}")
    public String addIncidencia(@PathVariable String id_alumno, Model model, HttpSession session){
        Incidencia incidencia=new Incidencia();
        incidencia.setUsuario(usuarioDao.getUsuarioPorID(id_alumno));
        model.addAttribute("incidencia", incidencia);
        session.setAttribute("id_alumno",id_alumno);
        return "incidencia/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("incidencia") Incidencia incidencia, BindingResult bindingResult, HttpSession sesion){
        Usuario promotor= (Usuario) sesion.getAttribute("user");
        Usuario alumno=usuarioDao.getUsuarioPorID((String) sesion.getAttribute("id_alumno"));
        sesion.removeAttribute("id_alumno");
        incidencia.setId_promotor(promotor.getUserId());
        incidencia.setUsuario(alumno);

        IncidenciaValidador incidenciaValidador = new IncidenciaValidador();
        incidenciaValidador.validate(incidencia, bindingResult);

        if(bindingResult.hasErrors())
            return "incidencia/add";
        usuarioDao.desactivateUsuario(incidencia.getUsuario().getUserId());
        incidenciaDao.addIncidencia((incidencia));
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id_alumno}/{fecha}", method = RequestMethod.GET)
    public String editIncidencia(Model model, @PathVariable String id_alumno, @PathVariable Date fecha){
        model.addAttribute("incidencia", incidenciaDao.getIncidencia(id_alumno, fecha));
        return "habilidad/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("incidencia") Incidencia incidencia, BindingResult bindingResult){
        IncidenciaValidador incidenciaValidador = new IncidenciaValidador();
        incidenciaValidador.validate(incidencia, bindingResult);
        if (bindingResult.hasErrors())
            return "incidencia/update";
        incidenciaDao.updateIncidencia(incidencia);
        return "redirect:list";
    }
}
