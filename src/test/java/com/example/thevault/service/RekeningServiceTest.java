package com.example.thevault.service;

import com.example.thevault.domain.mapping.repository.RootRepository;
import com.example.thevault.domain.model.Klant;
import com.example.thevault.domain.model.Rekening;
import com.example.thevault.support.exceptions.UserNotExistsException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;


class RekeningServiceTest {

    public static Klant bestaandeKlant;
    public static Klant nietBestaandeKlant;
    public static Klant nieuweKlant;
    public static RootRepository mockRepo;
    public static Rekening rekeningExpected;
    public static RekeningService rekeningServiceTest;
    public static Rekening nieuweRekening;

    @BeforeAll
    static void setUp() {
        bestaandeKlant = new Klant( "Henknr1", "fdsaljkl", "Hello", 1890393, LocalDate.of(1991, 1, 12));
        nietBestaandeKlant = new Klant( "HarryBeste", "210jklf", "", 101212, LocalDate.of(1991, 1, 12));
        nieuweKlant = new Klant( "ThomasBeste", "831hgtr", "", 1528719, LocalDate.of(1990, 5, 10));
        mockRepo = Mockito.mock(RootRepository.class);
        rekeningServiceTest = new RekeningService(mockRepo);
        rekeningExpected = new Rekening("INGB0001234567NL", 1000.0);
        bestaandeKlant.setRekening(rekeningExpected);
        rekeningExpected.setKlant(bestaandeKlant);
        nieuweRekening = new Rekening("NL20RABO9876543", 1000.0);
    }

    @Test
    void createIban() {
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("TVLT").buildRandom();
        assertThat(iban.toString()).contains("NL");
    }

    @Test
    void creeerRekening() {
        Rekening actual = rekeningServiceTest.creeerRekening(nieuweKlant);
        System.out.println(actual);
        assertThat(actual.getIban()).isNotNull();
        assertThat(actual.getSaldo()).isEqualTo(1000.0);
    }

    @Test
    void slaRekeningOp() {
        Mockito.when(mockRepo.slaRekeningOp(nieuweRekening)).thenReturn(nieuweRekening);

        Rekening actual = rekeningServiceTest.slaRekeningOp(nieuweRekening);
        System.out.println(actual);
        Rekening expected = nieuweRekening;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void vindRekeningVanKlant() {
        Mockito.when(mockRepo.vindKlantByGebruikersnaam(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);

        Rekening actual = rekeningServiceTest.vindRekeningVanKlant(bestaandeKlant);
        System.out.println(actual);
        Rekening expected = rekeningExpected;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

    @Test
    void vindRekeningVanNietBestaandeKlant() {
        Mockito.when(mockRepo.vindKlantByGebruikersnaam(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);

        try{
            rekeningServiceTest.vraagSaldoOpVanKlant(nietBestaandeKlant);
            fail("Moet een UserNotExistsException gooien");
        } catch (UserNotExistsException expected){
            System.out.println("Test geslaagd!");
        }
    }

    @Test
    void vraagSaldoOpVanKlant() {
        Mockito.when(mockRepo.vindKlantByGebruikersnaam(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);
        Mockito.when(mockRepo.vraagSaldoOpVanKlant(bestaandeKlant)).thenReturn(bestaandeKlant.getRekening().getSaldo());

        double actual = rekeningServiceTest.vraagSaldoOpVanKlant(bestaandeKlant);
        System.out.println(actual);
        double expected = 1000.0;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void vraagSaldoOpVanNietBestaandeKlant() {
        Mockito.when(mockRepo.vindKlantByGebruikersnaam(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);
        Mockito.when(mockRepo.vraagSaldoOpVanKlant(bestaandeKlant)).thenReturn(bestaandeKlant.getRekening().getSaldo());

        try{
            rekeningServiceTest.vraagSaldoOpVanKlant(nietBestaandeKlant);
            fail("Moet een UserNotExistsException gooien");
        } catch (UserNotExistsException expected){
            System.out.println("Test geslaagd!");
        }
    }

    @Test
    void wijzigSaldoVanKlant() {
        Mockito.when(mockRepo.vindKlantByGebruikersnaam(bestaandeKlant.getGebruikersnaam())).thenReturn(bestaandeKlant);
        Mockito.when(mockRepo.vindRekeningVanKlant(bestaandeKlant)).thenReturn(rekeningExpected);
        Mockito.when(mockRepo.wijzigSaldoVanKlant(bestaandeKlant, 2000.0)).thenReturn(bestaandeKlant.getRekening());

        Rekening rekeningActual = rekeningServiceTest.wijzigSaldoVanKlant(bestaandeKlant, 2000.0);
        System.out.println(rekeningActual);
        double expected = 2000.0;
        double actual = rekeningActual.getSaldo();
        assertThat(actual).isEqualTo(expected);
    }
}
