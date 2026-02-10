package at.iu.ipwa.ghostnet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class GeisternetzFormBean {

    @Inject
    private GeisternetzService service;

    private Geisternetz geisternetz = new Geisternetz();

    public String melden() {
        service.melden(geisternetz);
        return "liste?faces-redirect=true";
    }

    public Geisternetz getGeisternetz() {
        return geisternetz;
    }
}