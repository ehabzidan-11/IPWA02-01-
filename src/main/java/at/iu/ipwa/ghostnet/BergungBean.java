package at.iu.ipwa.ghostnet;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class BergungBean {

    @Inject
    private GeisternetzService service;

    private Long id;
    private Geisternetz geisternetz;

    private String name;
    private String telefon;

    // ✅ braucht JSF wegen <h:inputHidden>
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Geisternetz getGeisternetz() {
        return geisternetz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @PostConstruct
    public void init() {
        // Wenn id schon da ist (z.B. vom POST via hidden field), nichts überschreiben
        if (id == null) {
            String idParam = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("id");

            if (idParam != null && !idParam.isBlank()) {
                try {
                    id = Long.valueOf(idParam);
                } catch (NumberFormatException e) {
                    addError("Ungültige ID: " + idParam);
                    return;
                }
            }
        }

        if (id == null) {
            // Nur Hinweis, falls Seite direkt ohne Parameter geöffnet wurde
            addError("Keine ID übergeben. Bitte über die Liste aufrufen.");
            return;
        }

        geisternetz = service.findeById(id);
        if (geisternetz == null) {
            addError("Geisternetz mit ID " + id + " nicht gefunden.");
            return;
        }

        if (geisternetz.getBergendePerson() != null) {
            name = geisternetz.getBergendePerson().getName();
            telefon = geisternetz.getBergendePerson().getTelefon();
        }
    }

    public String speichern() {
        if (id == null) {
            addError("Keine gültige ID übergeben.");
            return null;
        }

        try {
            service.bergungUebernehmen(id, name, telefon);
            return "liste?faces-redirect=true";
        } catch (IllegalArgumentException ex) {
            addError(ex.getMessage());
            return null;
        } catch (Exception ex) {
          
            addError(ex.toString());
            ex.printStackTrace();
            return null;
        }
    }

    private void addError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", msg));
    }

}
