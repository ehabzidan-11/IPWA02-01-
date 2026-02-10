package at.iu.ipwa.ghostnet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class GeisternetzListeBean {

    @Inject
    private GeisternetzService service;

    public List<Geisternetz> getAlle() {
        return service.findeAlle();
    }

    public String geborgen(Long id) {
        service.setzeStatus(id, Status.GEBORGEN);
        return null;
    }

    public String verschollen(Long id) {
        service.setzeStatus(id, Status.VERSCHOLLEN);
        return null;
    }
}