package es.uji.ei1027.SkillSharing.Controller;

import es.uji.ei1027.SkillSharing.Model.Habilidad;
import es.uji.ei1027.SkillSharing.Model.Oferta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class OfertaValidador implements  Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Oferta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Oferta oferta = (Oferta) target;

        //-----------------------------Descripción-----------------------------
        if (oferta.getDescripcion().trim().equals("")) //descripción vacía
            errors.rejectValue("descripcion", "Oferta_campo_descripcion_sin_rellenar",
                    "Debe facilitarse la descripción.");
        if (oferta.getFecha_inicio()==null)
            errors.rejectValue("fecha_inicio", "fecha inicio vacia",
                    "Debe facilitarse la fecha de inicio.");
        if (oferta.getFecha_fin()==null)
            errors.rejectValue("fecha_fin", "fecha fin vacia",
                    "Debe facilitarse la fecha de fin.");
        if (oferta.getFecha_inicio()!=null&& oferta.getFecha_fin()!=null &&oferta.getFecha_inicio().compareTo(oferta.getFecha_fin())>0)
            errors.rejectValue("fecha_fin","compulsory","La fecha de fin debe ser mayor que la de inicio.");
        if (oferta.getFecha_inicio()!=null&& oferta.getFecha_inicio().compareTo(LocalDate.now())<0)
            errors.rejectValue("fecha_inicio","fecha invalida","La fecha de inicio no puede ser anterior a hoy.");
        if (oferta.getFecha_fin()!=null&& oferta.getFecha_fin().compareTo(LocalDate.now())<0)
            errors.rejectValue("fecha_fin","fecha invalida","La fecha de finalización no puede ser anterior a hoy.");
        if (oferta.getNombre_habilidad().trim().equals("0"))
            errors.rejectValue("nombre_habilidad", "nombre habilidad vacio",
                    "Debe seleccionar una habilidad.");
        if (oferta.getTipo()==null)
            errors.rejectValue("tipo", "tipo vacio",
                    "Debe seleccionar el tipo.");
    }

    public void validate2(Object target, Errors errors) {
        Oferta oferta = (Oferta) target;

        //-----------------------------Descripción-----------------------------
        if (oferta.getDescripcion().trim().equals("")) //descripción vacía
            errors.rejectValue("descripcion", "Oferta_campo_descripcion_sin_rellenar",
                    "Debe facilitarse la descripción.");

        if (oferta.getFecha_inicio()==null)
            errors.rejectValue("fecha_inicio", "fecha inicio vacia",
                    "Debe facilitarse la fecha de inicio.");
        if (oferta.getFecha_fin()==null)
            errors.rejectValue("fecha_fin", "fecha fin vacia",
                    "Debe facilitarse la fecha de fin.");
        if (oferta.getFecha_inicio()!=null&& oferta.getFecha_fin()!=null &&oferta.getFecha_inicio().compareTo(oferta.getFecha_fin())>0)
            errors.rejectValue("fecha_fin","compulsory","La fecha de fin debe ser mayor que la de inicio.");
        if (oferta.getFecha_inicio()!=null&& oferta.getFecha_inicio().compareTo(LocalDate.now())<0)
            errors.rejectValue("fecha_inicio","fecha invalida","La fecha de inicio no puede ser anterior a hoy.");
        if (oferta.getFecha_fin()!=null&& oferta.getFecha_fin().compareTo(LocalDate.now())<0)
            errors.rejectValue("fecha_fin","fecha invalida","La fecha de finalización no puede ser anterior a hoy.");
    }
}
