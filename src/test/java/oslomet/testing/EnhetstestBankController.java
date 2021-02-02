package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void endreKundeInfo_loggetInn(){
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");


        String resultat = bankController.endre(kunde1);
        Assert.assertEquals("OK", resultat);
    }
    @Test
    public void endreKundeInfo_ikkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);
        //arrange
        Kunde kunde1 = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        //act
        String resultat = bankController.endre(kunde1);

        //assert
        Assert.assertNull(resultat);
    }
    @Test
    public void hentBetalinger_loggetInn() {
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "02.02.2020", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //assert
        assertEquals(transaksjoner, resultat);
    }
    @Test
    public void hentBetalinger_ikkeLoggetInn(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "02.02.2020", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn(null);
        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //assert
        assertNull(resultat);
    }
    @Test
    public void hentTransaksjoner_loggetInn(){
        //arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", transaksjoner);
        konti.add(konto1);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konto1);

        //act
        Konto resultat = bankController.hentTransaksjoner("01010110523", "", "");

        //assert
        assertEquals(konto1, resultat);
    }
    @Test
    public void hentTransaksjoner_ikkeLoggetInn(){
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner("01010110523", "", "");

        // assert
        assertNull(resultat);
    }
    @Test
    public void utforBetaling_loggetInn(){
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.utforBetaling(1)).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertEquals(transaksjoner, resultat);
    }
    @Test
    public void utforBetaling_ikkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.utforBetaling(1);

        assertNull(resultat);
    }
    @Test
    public void hentSaldi_loggetInn(){
        //arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        when(repository.hentSaldi(anyString())).thenReturn(konti);
        when(sjekk.loggetInn()).thenReturn("01010110523");

        //act
        List<Konto> resultat = bankController.hentSaldi();

        //assert
        assertEquals(konti, resultat);
    }
    @Test
    public void hentSaldi_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }
    @Test
    public void registrerBetaling_loggetInn(){

        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "02.02.2020", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Ok");

        String resultat = bankController.registrerBetaling(transaksjon1);

        assertEquals("Ok", resultat);
    }
    @Test
    public void registrerBetaling_ikkeLoggetInn(){
        // arrange
        List<Transaksjon> transaksjoner = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "01010110523", 33, "02.02.2020", "transaksjon", "avventer", "12345678901");
        transaksjoner.add(transaksjon1);
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(transaksjon1);

        // assert
        assertNull(resultat);
    }
    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }
}

