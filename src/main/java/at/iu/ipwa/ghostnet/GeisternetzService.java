package at.iu.ipwa.ghostnet;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class GeisternetzService {

    @PersistenceContext
    private EntityManager em;

    public void melden(Geisternetz g) {
        if (g.getStatus() == null) g.setStatus(Status.GEMELDET);
        em.persist(g);
    }

    public List<Geisternetz> findeAlle() {
        return em.createQuery("SELECT g FROM Geisternetz g ORDER BY g.id DESC", Geisternetz.class)
                 .getResultList();
    }

    public List<Geisternetz> findeOffene() {
        return em.createQuery("SELECT g FROM Geisternetz g WHERE g.status <> :s ORDER BY g.id DESC", Geisternetz.class)
                 .setParameter("s", Status.GEBORGEN)
                 .getResultList();
    }

    public void setzeStatus(Long id, Status status) {
        Geisternetz g = em.find(Geisternetz.class, id);
        if (g == null) return;
        g.setStatus(status); // managed -> update automatisch
    }
}