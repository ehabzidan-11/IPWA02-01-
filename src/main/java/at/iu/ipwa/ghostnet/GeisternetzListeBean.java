package at.iu.ipwa.ghostnet;

import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GeisternetzListeBean {

    @Inject
    private GeisternetzService service;

    private Long selectedId;

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public List<Geisternetz> getAlle() {
        return service.findeAlle();
    }

    public String geborgen() {
        service.setzeGeborgen(selectedId);
        return "liste?faces-redirect=true"; // optional
    }

   public String verschollen() {
    FacesContext fc = FacesContext.getCurrentInstance();

    try {
        service.setzeVerschollen(selectedId);

 
        return "liste?faces-redirect=true";  
    } catch (Exception ex) {

    
        String msg = ex.getMessage();
        if ((msg == null || msg.isBlank()) && ex.getCause() != null) {
            msg = ex.getCause().getMessage();
        }
        if (msg == null || msg.isBlank()) {
            msg = "Verschollen melden nur mit Name und Telefonnummer.";
        }

        fc.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                "Fehler",
                "Netz-ID " + selectedId + ": " + msg
        ));

      
        return null;
    }
}

    public List<Geisternetz> getOffen() {
        return service.findeNochZuBergen();
    }

}
