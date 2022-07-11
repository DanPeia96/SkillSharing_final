package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.ColaboracionDao;
import es.uji.ei1027.SkillSharing.Dao.HabilidadDao;
import es.uji.ei1027.SkillSharing.Dao.SolicitudDao;
import es.uji.ei1027.SkillSharing.Model.Habilidad;
import es.uji.ei1027.SkillSharing.Model.Solicitud;
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


@Controller
@RequestMapping("/solicitud")
public class SolicitudControlador {
    private SolicitudDao solicitudDao;
    private ColaboracionDao colaboracionDao;

    @Autowired
    public void setSolicitudDao(SolicitudDao sDao){
        this.solicitudDao = sDao;
    }

    @Autowired
    public void setColaboracionDao(ColaboracionDao colaboracionDao){
        this.colaboracionDao=colaboracionDao;
    }

    @RequestMapping("/listPendientes")
    public String listsolicitudesPendientes(Model model, HttpSession session){
        Usuario usuario= (Usuario) session.getAttribute("user");
        model.addAttribute("solicitudes", solicitudDao.getSolicitudesPedientes(usuario.getUserId()));
        return "solicitud/list";
    }

    @RequestMapping(value = "/add/{idOferta}")
    public String add(@PathVariable String idOferta, HttpSession session){
        Usuario usuario= (Usuario) session.getAttribute("user");
        solicitudDao.addSolicitud(idOferta, usuario.getUserId());
        return "redirect:../listPendientes";
    }

    @RequestMapping(value = "/delete/{codigo_solicitud}")
    public String processDelete(@PathVariable String codigo_solicitud){
        solicitudDao.aceptaOBorraSolicitud(codigo_solicitud);
        return "redirect:../listPendientes";
    }

    @RequestMapping(value = "/aceptar/{codigo_solicitud}")
    public String processAceptar(@PathVariable String codigo_solicitud){
        Solicitud solicitud=solicitudDao.getSolicitud(codigo_solicitud);
        System.out.println(codigo_solicitud);
        System.out.println(solicitud);
        colaboracionDao.addColaboracion(solicitud);
        solicitudDao.aceptaOBorraSolicitud(codigo_solicitud);
        return "redirect:../listPendientes";
    }
}