package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {
    @InjectMocks
    private AdminKundeController AKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlle_loggetInn() {
        List<Kunde> kunder = new ArrayList<>();
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                            "1234", "Asker", "41345678", "pass1234");
        Kunde kunde2 = new Kunde("02028922334", "Lise", "Olavsen", "Osloveien 77",
                            "0850", "Oslo", "974568342", "lise89");
        kunder.add(kunde1);
        kunder.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("01019912543");

        when(repository.hentAlleKunder()).thenReturn(kunder);

        // act
        List<Kunde> resultat = AKundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);
    }

    @Test
    public void hentAlle_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List<Kunde> resultat = AKundeController.hentAlle();

        assertNull(resultat);
    }

    @Test
    public void lagreKunde_loggetInnOk() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn("01019912543");

        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Ok");

        String resultat = AKundeController.lagreKunde(kunde1);

        assertEquals("Ok", resultat);
    }

    @Test
    public void lagreKunde_loggetInnFeil() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn("01019912543");

        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        String resultat = AKundeController.lagreKunde(kunde1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void lagreKunde_ikkeLoggetInn() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = AKundeController.lagreKunde(kunde1);

        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void endre_loggetInnOk() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn("01019912543");

        when(repository.endreKundeInfo((any(Kunde.class)))).thenReturn("Ok");

        String resultat = AKundeController.endre(kunde1);

        assertEquals("Ok", resultat);
    }

    @Test
    public void endre_loggetInnFeil() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn("01019912543");

        when(repository.endreKundeInfo((any(Kunde.class)))).thenReturn("Feil");

        String resultat = AKundeController.endre(kunde1);

        assertEquals("Feil", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn() {
        Kunde kunde1 = new Kunde("01019912543", "Ole", "Hansen", "Askerveien 52",
                "1234", "Asker", "41345678", "pass1234");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = AKundeController.endre(kunde1);

        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slett_loggetInnOk() {
        String personnummer = "01019912543";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.slettKunde((any(String.class)))).thenReturn("Ok");

        String resultat = AKundeController.slett(personnummer);

        assertEquals("Ok", resultat);
    }

    @Test
    public void slett_loggetInnFeil() {
        String personnummer = "01019912543";

        when(sjekk.loggetInn()).thenReturn(personnummer);

        when(repository.slettKunde((any(String.class)))).thenReturn("Feil");

        String resultat = AKundeController.slett(personnummer);

        assertEquals("Feil", resultat);
    }

    @Test
    public void slett_ikkeLoggetInn() {
        String personnummer = "01019912543";

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = AKundeController.slett(personnummer);

        assertEquals("Ikke logget inn", resultat);
    }

}
