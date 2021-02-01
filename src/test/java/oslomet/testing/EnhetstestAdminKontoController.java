package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    private AdminKontoController AKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_loggetInn() {
        List<Konto> kontoer = new ArrayList<>();
        Konto enKonto = new Konto("01010110523", "23414245631", 123456, "Sparekonto",
                                "NOK", null);
        Konto enKonto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        kontoer.add(enKonto);
        kontoer.add(enKonto2);

        when(sjekk.loggetInn()).thenReturn("12345678901");

        when(repository.hentAlleKonti()).thenReturn(kontoer);

        // act
        List<Konto> resultat = AKontoController.hentAlleKonti();

        // assert
        assertEquals(kontoer, resultat);
    }

    @Test
    public void hentAlleKonti_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);
        List<Konto> resultat = AKontoController.hentAlleKonti();
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_loggetInnOk() {
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto((any(Konto.class)))).thenReturn("Ok");

        String resultat = AKontoController.registrerKonto(enKonto);

        assertEquals("Ok", resultat);
    }

    @Test
    public void registrerKonto_loggetInnFeil() {
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto((any(Konto.class)))).thenReturn("Feil");

        String resultat = AKontoController.registrerKonto(enKonto);

        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerKonto_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        String resultat = AKontoController.registrerKonto(enKonto);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKonto_loggetInnOk() {
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.endreKonto((any(Konto.class)))).thenReturn("Ok");

        String resultat = AKontoController.endreKonto(enKonto);

        assertEquals("Ok", resultat);
    }

    @Test
    public void endreKonto_loggetInnFeil() {
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.endreKonto((any(Konto.class)))).thenReturn("Feil");

        String resultat = AKontoController.endreKonto(enKonto);

        assertEquals("Feil", resultat);
    }

    @Test
    public void endreKonto_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        String resultat = AKontoController.endreKonto(enKonto);

        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_loggetInnOk() {
        String kontoNr = "123456789";
        when(sjekk.loggetInn()).thenReturn(kontoNr);
        when(repository.slettKonto((any(String.class)))).thenReturn("Ok");

        String resultat = AKontoController.slettKonto(kontoNr);

        assertEquals("Ok", resultat);
    }

    @Test
    public void slettKonto_loggetInnFeil() {
        String kontoNr = "123456789";
        when(sjekk.loggetInn()).thenReturn(kontoNr);
        when(repository.slettKonto((any(String.class)))).thenReturn("Feil kontonummer");

        String resultat = AKontoController.slettKonto(kontoNr);

        assertEquals("Feil kontonummer", resultat);
    }

    @Test
    public void slettKonto_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        String kontoNr = "123456789";

        String resultat = AKontoController.slettKonto(kontoNr);

        assertEquals("Ikke innlogget", resultat);
    }

}
