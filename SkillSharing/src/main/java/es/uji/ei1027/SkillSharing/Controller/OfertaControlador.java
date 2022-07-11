package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.HabilidadDao;
import es.uji.ei1027.SkillSharing.Dao.OfertaDao;
import es.uji.ei1027.SkillSharing.Controller.OfertaValidador;
import es.uji.ei1027.SkillSharing.Model.Oferta;
import es.uji.ei1027.SkillSharing.Model.Usuario;
import es.uji.ei1027.SkillSharing.Model.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.List;


@Controller
@RequestMapping("/oferta")
public class OfertaControlador {
    private OfertaDao ofertaDao;
    private HabilidadDao habilidadDao;

    @Autowired
    public void setOfertaDao(OfertaDao oDao){
        this.ofertaDao = oDao;
    }

    @Autowired
    public void setHabilidadDao(HabilidadDao hDao){
        this.habilidadDao = hDao;
    }

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("ofertas", ofertaDao.getOfertas());
        return "oferta/list";
    }

    @RequestMapping("/vermas/{codigo_oferta}")
    public String listVermas(Model model, @PathVariable String codigo_oferta){
        model.addAttribute("oferta", ofertaDao.getOferta(codigo_oferta));
        return "oferta/verOferta";
    }

    @RequestMapping("/list/tipo")
    public String listDemandas(Model model, @RequestParam String tipo){
        if (tipo.equals("oferta"))
            model.addAttribute("ofertas", ofertaDao.getOfertas2());
        else if (tipo. equals("demanda"))model.addAttribute("ofertas", ofertaDao.getDemandas());
        else model.addAttribute("ofertas", ofertaDao.getOfertas());
        return "oferta/list";
    }

    @RequestMapping("/listpr")
    public String list_prOfertas(Model model){
        model.addAttribute("ofertaspr", ofertaDao.getOfertas());
        return "oferta/listpr";
    }

    @RequestMapping(value = "/misofertas")
    public String list_misOfertas(Model model, HttpSession session){
        Usuario usuario=(Usuario) session.getAttribute("user");
        model.addAttribute("misofertas", ofertaDao.getOfertasByUser(usuario.getUserId()));
        return "oferta/misofertas";
    }

    @RequestMapping(value = "/misofertas/tipo")
    public String list_misOfertas2(Model model, HttpSession session, @RequestParam String tipo) {
        Usuario usuario=(Usuario) session.getAttribute("user");
        String  userId=usuario.getUserId();
        if (tipo.equals("oferta"))
            model.addAttribute("misofertas", ofertaDao.getOfertasByUser2(userId));
        else if (tipo. equals("demanda"))model.addAttribute("misofertas", ofertaDao.getDemandasByUser(userId));
        else model.addAttribute("misofertas", ofertaDao.getOfertasByUser(userId));
        return "oferta/misofertas";
    }

    @RequestMapping(value = "/veroferta/{codigo_oferta}", method = RequestMethod.GET)
    public String getOfertaById(Model model, @PathVariable String codigo_oferta){
        model.addAttribute("oferta", ofertaDao.getOfertasByUser(codigo_oferta));
        return "oferta/verOferta/"+codigo_oferta;
    }

    @RequestMapping(value = "/add")
    public String addOferta(Model model){
        List<Habilidad> habilidades=habilidadDao.getHabilidadesActivas();
        model.addAttribute("habilidades",habilidades);
        model.addAttribute("oferta", new Oferta());
        return "oferta/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oferta") Oferta oferta, BindingResult bindingResult, HttpSession sesion, Model model){
        Usuario usuario=(Usuario) sesion.getAttribute("user");

        OfertaValidador ofertaValidator = new OfertaValidador();

        ofertaValidator.validate(oferta, bindingResult);
        if (usuario.getSaldo_horas()<-20 && oferta.getTipo())
            bindingResult.rejectValue("tipo", "saldo_negativo", "No puedes crear ofertas con el saldo de horas menor que -20");
        if (bindingResult.hasErrors()) {
            model.addAttribute("habilidades", habilidadDao.getHabilidadesActivas());
            return "oferta/add";
        }
        oferta.setNivel_habilidad(Integer.parseInt(oferta.getNombre_habilidad().split(" ")[1]));
        oferta.setNombre_habilidad(oferta.getNombre_habilidad().split(" ")[0]);

        sesion.setAttribute("oferta",oferta);
        List<Oferta> ofertas=ofertaDao.getOfertasByHabilidad(oferta.getNombre_habilidad(), oferta.getNivel_habilidad(), oferta.getTipo());
        if (!ofertas.isEmpty()){
            model.addAttribute("ofertas",ofertas);
            return "oferta/listexistentes";
        }
        ofertaDao.addOferta((oferta));
        return "redirect:misofertas";
    }

    @RequestMapping(value = "/add2")
    public String processAddSubmit(HttpSession sesion, Model model){
        Oferta oferta= (Oferta) sesion.getAttribute("oferta");

        Usuario usuario=(Usuario) sesion.getAttribute("user");
        oferta.setUsuario(usuario);

        System.out.println("________________________________________________________________________");
        System.out.println("id: "+oferta.getCodigo_oferta());
        System.out.println("f_ INI: "+oferta.getFecha_inicio());
        System.out.println("F_ fin: "+oferta.getFecha_fin());
        System.out.println("id_usuario: "+oferta.getUsuario());
        System.out.println("tipo: "+oferta.getTipo());
        System.out.println("nombre habilidad: "+oferta.getNombre_habilidad());
        System.out.println("nivel habilidad: "+oferta.getNivel_habilidad());
        System.out.println("________________________________________________________________________");


        sesion.removeAttribute("oferta");
        ofertaDao.addOferta((oferta));
        return "redirect:misofertas";
    }

    @RequestMapping(value="/update/{codigo_oferta}", method = RequestMethod.GET)
    public String editOferta(Model model, @PathVariable String codigo_oferta, HttpSession session){
        model.addAttribute("oferta", ofertaDao.getOferta(codigo_oferta));
        session.setAttribute("codigo_oferta",codigo_oferta);
        return "oferta/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oferta") Oferta oferta, BindingResult bindingResult,HttpSession session, Model model){
        OfertaValidador ofertaValidador = new OfertaValidador();
        Oferta oferta1=ofertaDao.getOferta((String) session.getAttribute("codigo_oferta"));
        oferta1.setFecha_inicio(oferta.getFecha_inicio());
        oferta1.setFecha_fin(oferta.getFecha_fin());
        oferta1.setDescripcion(oferta.getDescripcion());
        ofertaValidador.validate2(oferta1, bindingResult);
        model.addAttribute("oferta",oferta1);

        if (bindingResult.hasErrors())
            return "oferta/update";
        session.removeAttribute("codigo_oferta");
        ofertaDao.updateOferta(oferta1);
        return "redirect:misofertas";
    }

    @RequestMapping(value = "/delete/{codigo_oferta}")
    public String processDelete(@PathVariable String codigo_oferta, HttpSession sesion){
        Usuario usuario=(Usuario) sesion.getAttribute("user");
        ofertaDao.deleteOferta(codigo_oferta);
        if(usuario.esAdmin()){
            return "redirect:../listpr/";

        }else {
            return "redirect:../misofertas";
        }
    }

}
