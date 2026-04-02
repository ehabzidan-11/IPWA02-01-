package at.iu.ipwa.ghostnet;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Map;

@Named
@RequestScoped
public class GeisternetzFormBean {

    @Inject
    private GeisternetzService service;

    private Geisternetz geisternetz = new Geisternetz();

    @PostConstruct
    public void init() {
        geisternetz.setBergendePerson(new Person());
    }

    public Geisternetz getGeisternetz() {
        return geisternetz;
    }

    // normale Bindung (Ajax setzt das)
    public boolean isAnonymGemeldet() {
        return geisternetz.isAnonymGemeldet();
    }

    public void setAnonymGemeldet(boolean anonym) {
        geisternetz.setAnonymGemeldet(anonym);
    }

    /**
     * ✅ WICHTIG: bei Validierungsfehlern bleibt das Model manchmal alt. Diese
     * Methode liest den aktuellen Checkbox-Wert aus dem Request-Parameter,
     * damit die View trotzdem korrekt disabled rendert.
     */
    public boolean isAnonymGemeldetEffective() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        // Client-ID der Checkbox: reportForm:anonym
        String val = params.get("reportForm:anonym");
        if (val != null) {
            // Checkbox sendet "on" wenn angehakt
            return "on".equalsIgnoreCase(val) || "true".equalsIgnoreCase(val);
        }
        return geisternetz.isAnonymGemeldet();
    }

    public String melden() {
        // für Persistenz: wenn anonym, Person entfernen
        if (isAnonymGemeldetEffective()) {
            geisternetz.setAnonymGemeldet(true);
            geisternetz.setBergendePerson(null);
        }
        service.melden(geisternetz);
        return "liste?faces-redirect=true";
    }
}
