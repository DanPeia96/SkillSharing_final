package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.ColaboracionDao;
import es.uji.ei1027.SkillSharing.Dao.UsuarioDao;
import es.uji.ei1027.SkillSharing.Model.Colaboracion;
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
@RequestMapping("/colaboracion")
public class ColaboracionControllador {
    private ColaboracionDao colaboracionDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setColaboracionDao(ColaboracionDao cDao, UsuarioDao usuarioDao){
        this.colaboracionDao = cDao;
        this.usuarioDao=usuarioDao;
    }

    @RequestMapping("/list")
    public String listColaboraciones(Model model){
        model.addAttribute("colaboraciones", colaboracionDao.getColaboraciones());
        return "colaboracion/list";
    }

    @RequestMapping("/misColaboraciones")
    public String misColaboraciones(Model model, HttpSession session){
        Usuario usuario= (Usuario) session.getAttribute("user");
        model.addAttribute("colaboraciones", colaboracionDao.getMisColaboraciones(usuario.getUserId()));
        System.out.println(colaboracionDao.getMisColaboraciones(usuario.getUserId()));
        return "colaboracion/misColaboraciones";
    }

    @RequestMapping(value="/update/{codigo_colaboracion}", method = RequestMethod.GET)
    public String editColaboracion(Model model, @PathVariable String codigo_colaboracion, HttpSession session){
        model.addAttribute("colaboracion", colaboracionDao.getColaboracion(codigo_colaboracion));
        session.setAttribute("codigo_colaboracion",codigo_colaboracion);
        return "colaboracion/valorar";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("colaboracion") Colaboracion colaboracion, BindingResult bindingResult, HttpSession session){
        Colaboracion colaboracion2=colaboracionDao.getColaboracion((String) session.getAttribute("codigo_colaboracion"));
        colaboracion2.setEvaluacion(colaboracion.getEvaluacion());
        ColaboracionValidador colaboracionValidador=new ColaboracionValidador();
        colaboracionValidador.validateValoracion(colaboracion2,bindingResult);
        if (bindingResult.hasErrors())
            return "colaboracion/valorar";
        colaboracion2.setHoras(colaboracion.getHoras());
        Usuario usuario= (Usuario) session.getAttribute("user");
        Usuario usuario2=colaboracion2.getSolicitud().getUsuario_solicitante();
        colaboracionDao.updateColaboracion(colaboracion2);
        if (colaboracion2.getSolicitud().getOferta().getTipo()){
            if (colaboracion2.getSolicitud().getUsuario_solicitante().getUserId().equals(usuario.getUserId())) {
                usuarioDao.setSaldo(usuario.getSaldo_horas()+colaboracion2.getHoras(), usuario.getUserId());
                usuarioDao.setSaldo(usuario2.getSaldo_horas()-colaboracion2.getHoras(),usuario2.getUserId());
            }
            else{
                usuarioDao.setSaldo(usuario.getSaldo_horas()-colaboracion2.getHoras(), usuario.getUserId());
                usuarioDao.setSaldo(usuario2.getSaldo_horas()+colaboracion2.getHoras(),usuario2.getUserId());
            }
        }
        else {
            if (colaboracion2.getSolicitud().getUsuario_solicitante().getUserId().equals(usuario.getUserId())) {
                usuarioDao.setSaldo(usuario.getSaldo_horas()-colaboracion2.getHoras(), usuario.getUserId());
                usuarioDao.setSaldo(usuario2.getSaldo_horas()+colaboracion2.getHoras(),usuario2.getUserId());
            }
            else{
                usuarioDao.setSaldo(usuario.getSaldo_horas()+colaboracion2.getHoras(), usuario.getUserId());
                usuarioDao.setSaldo(usuario2.getSaldo_horas()-colaboracion2.getHoras(),usuario2.getUserId());
            }
        }
        return "redirect:misColaboraciones";
    }

    @RequestMapping(value="/cancelar/{codigo_colaboracion}", method = RequestMethod.GET)
    public String cancelarColaboracion(Model model, @PathVariable String codigo_colaboracion){
        colaboracionDao.cancelarColaboracion(codigo_colaboracion);
        return "redirect:../misColaboraciones";
    }
}
