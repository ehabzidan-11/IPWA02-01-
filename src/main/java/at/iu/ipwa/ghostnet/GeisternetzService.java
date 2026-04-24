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
        if (g.getStatus() == null) {
            g.setStatus(Status.GEMELDET);
        }

        if (g.isAnonymGemeldet()) {
            g.setBergendePerson(null);
        }

        em.persist(g);
    }

    public Geisternetz findeById(Long id) {
        return em.find(Geisternetz.class, id);
    }

    public List<Geisternetz> findeAlle() {
        return em.createQuery(
                "SELECT g FROM Geisternetz g ORDER BY g.id DESC",
                Geisternetz.class
        ).getResultList();
    }

    public List<Geisternetz> findeNochZuBergen() {
        return em.createQuery(
                "SELECT g FROM Geisternetz g WHERE g.status IN (:a, :b) ORDER BY g.id DESC",
                Geisternetz.class
        )
                .setParameter("a", Status.GEMELDET)
                .setParameter("b", Status.BERGUNG_BEVORSTEHEND)
                .getResultList();
    }

    // MUST 2: Bergung übernehmen
    public void bergungUebernehmen(Long geisternetzId, String name, String telefon) {
        Geisternetz g = em.find(Geisternetz.class, geisternetzId);
        if (g == null) {
            return;
        }

        if (name == null || name.isBlank() || telefon == null || telefon.isBlank()) {
            throw new IllegalArgumentException("Name und Telefonnummer sind Pflicht.");
        }

        Person p = g.getBergendePerson();
        if (p == null) {
            p = new Person();
            g.setBergendePerson(p);
        }

        p.setName(name);
        p.setTelefon(telefon);

        // wenn eine Person eingetragen wird, ist es nicht anonym
        g.setAnonymGemeldet(false);

        // Status setzen
        g.setStatus(Status.BERGUNG_BEVORSTEHEND);

        // falls Person neu ist (kein Cascade), speichern:
        if (p.getId() == null) {
            em.persist(p);
        }
    }

    // MUST 4
    public void setzeGeborgen(Long id) {
        Geisternetz g = em.find(Geisternetz.class, id);
        if (g == null) {
            return;
        }
        g.setStatus(Status.GEBORGEN);
    }

    // COULD 7 + Regel: nicht anonym verschollen
    public void setzeVerschollen(Long id) {
        Geisternetz g = em.find(Geisternetz.class, id);
        if (g == null) {
            return;
        }

        Person p = g.getBergendePerson();
        if (p == null || p.getName() == null || p.getName().isBlank()
                || p.getTelefon() == null || p.getTelefon().isBlank()) {
            throw new IllegalArgumentException("Verschollen melden nur mit Name und Telefonnummer.");
        }

        g.setStatus(Status.VERSCHOLLEN);
    }
    
   
    
}
