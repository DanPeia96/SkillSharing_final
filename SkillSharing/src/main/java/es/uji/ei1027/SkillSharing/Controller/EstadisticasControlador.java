package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Dao.ColaboracionDao;
import es.uji.ei1027.SkillSharing.Dao.EstadisticasDao;
import es.uji.ei1027.SkillSharing.Dao.HabilidadDao;
import es.uji.ei1027.SkillSharing.Dao.OfertaDao;
import es.uji.ei1027.SkillSharing.Model.Estadistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EstadisticasControlador{
    private EstadisticasDao estadisticasDao;
    private HabilidadDao habilidadDao;
    private OfertaDao ofertaDao;
    private ColaboracionDao colaboracionDao;

    @Autowired
    public void setOffeRequestDao(EstadisticasDao estadisticasDao, HabilidadDao habilidadDao, OfertaDao ofertaDao, ColaboracionDao colaboracionDao) {
        this.estadisticasDao=estadisticasDao;
        this.habilidadDao=habilidadDao;
        this.ofertaDao=ofertaDao;
        this.colaboracionDao=colaboracionDao;
    }

    @RequestMapping("/estadisticas")
    public String estadisticas(Model model){

        List<Estadistica> eo=estadisticasDao.estudiantesConMasOfertas();
        List<Estadistica> ec=estadisticasDao.estudiantesConMasColaboraciones();
        List<Estadistica> ei=estadisticasDao.estudiantesConMasIncidencias();

        model.addAttribute("eo",eo);
        model.addAttribute("ec",ec);
        model.addAttribute("ei",ei);

        return "estadisticas/list";
    }
}
