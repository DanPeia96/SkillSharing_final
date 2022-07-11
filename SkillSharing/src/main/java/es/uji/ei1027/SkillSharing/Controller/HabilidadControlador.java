package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.HabilidadDao;
import es.uji.ei1027.SkillSharing.Model.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/habilidad")
public class HabilidadControlador {
    private HabilidadDao habilidadDao;

    @Autowired
    public void setHabilidadDao(HabilidadDao hDao){
        this.habilidadDao = hDao;
    }

    @RequestMapping("/list")
    public String listHabilidades(Model model){
        model.addAttribute("habilidades", habilidadDao.getHabilidades());
        return "habilidad/list";
    }

    @RequestMapping(value = "/add")
    public String addHabilidad(Model model){
        model.addAttribute("habilidad", new Habilidad());
        return "habilidad/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("habilidad") Habilidad habilidad, BindingResult bindingResult){
        HabilidadValidador habilidadValidador = new HabilidadValidador();
        habilidadValidador.validate(habilidad, bindingResult);
        if(habilidadDao.getHabilidad(habilidad.getNombre(),habilidad.getNivel())!=null) {
            bindingResult.rejectValue("nivel","duplicado","Ya existe una habilidad con el mismo nombre y nivel.");
            System.out.println(bindingResult.getFieldErrors());
        }
        if(bindingResult.hasErrors())
            return "habilidad/add";
        habilidadDao.addHabilidad((habilidad));
        return "redirect:list";
    }

    @RequestMapping(value="/update/{nombre}/{nivel}", method = RequestMethod.GET)
    public String editHabilidad(Model model, @PathVariable String nombre, @PathVariable String nivel, HttpSession session){
        model.addAttribute("habilidad", habilidadDao.getHabilidad(nombre, Integer.parseInt(nivel)));
        session.setAttribute("nivel",nivel);
        return "habilidad/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("habilidad") Habilidad habilidad, BindingResult bindingResult, HttpSession session){
        Habilidad habilidad1=habilidadDao.getHabilidad(habilidad.getNombre(), Integer.parseInt((String) session.getAttribute("nivel")));
        habilidad1.setDescripcion(habilidad.getDescripcion());
        HabilidadValidador habilidadValidador = new HabilidadValidador();
        habilidadValidador.validate2(habilidad1, bindingResult);
        habilidad.setNivel(habilidad1.getNivel());
        if (bindingResult.hasErrors())
            return "habilidad/update";
        session.removeAttribute("nivel");
        habilidadDao.updateHabilidad(habilidad1);
        return "redirect:list";
    }

    @RequestMapping(value="/activar/{nombre}/{nivel}", method = RequestMethod.GET)
    public String activaHabilidad(Model model, @PathVariable String nombre, @PathVariable String nivel){
        model.addAttribute("habilidad", habilidadDao.activateHabilidad(nombre, Integer.parseInt(nivel)));
        return "redirect:/habilidad/list";
    }

    @RequestMapping(value="/desactivar/{nombre}/{nivel}", method = RequestMethod.GET)
    public String desactivaHabilidad(Model model, @PathVariable String nombre, @PathVariable String nivel){
        model.addAttribute("habilidad", habilidadDao.desactivateHabilidad(nombre, Integer.parseInt(nivel)));
        return "redirect:/habilidad/list";
    }
}