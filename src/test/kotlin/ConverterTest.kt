import org.junit.Test
import kotlin.test.assertEquals

class ConverterTest {

  val text = """
        |Lista transakcji;;;;;ING Bank �l�ski S.A. ul. Jakastam 1, 55-033 Warszawa www.ingbank.pl;;;;;;;;;;;;;;;
        |Dokument nr 0011141590_152342;
        |Wygenerowany dnia: 2012-09-10, 11:59;;;;;;;;;;;;;;;;;;;;
        |
        |Dane U�ytkownika:;
        |"HA�A MICHA�, MACKIEWICZA 12/35 42-530 MORZ�W";
        |
        |Wybrane rachunki:;
        |KONTO Direct (PLN);;'28 1358 1312 1000 0290 9345 5649';
        |
        |Zastosowane kryteria wyboru;;;;;Podsumowanie;;
        |
        |Zakres dat:;2014-09-01 - 2015-02-10;Typy transakcji:;wszystkie;;Liczba transakcji:;379;
        |
        |;;;;;Suma uzna� (22):;123,10;PLN;;;;;;;;;;;;;
        |
        |Ukryto saldo po transakcji;;;;;Suma obci��e� (350):;96,93;PLN;;;;;;;;;;;;;
        |
        |Data transakcji;Data ksi�gowania;Dane kontrahenta;Tytu�;Nr rachunku;Nazwa banku;Szczeg�y;Nr transakcji;Kwota transakcji (waluta rachunku);Waluta;Kwota blokady/zwolnienie blokady;Waluta;Kwota p�atno�ci w walucie;Waluta;;;;;;;
        |2013-09-08;;"PAYPAL SPOTIFY 35314369001 GB "; P�atno�� kart� 08.09.2013 Nr karty 5253xx4968;'';;;;;;-19,99;PLN;;;;;;;;;
        |2013-09-08;;"FIRMA ROGALA SP Z OO MLOSZOWA PL "; P�atno�� kart� 08.09.2013 Nr karty 5253xx4968;'';;;;;;-16,67;PLN;;;;;;;;;
        |2013-09-07;2013-09-09;"ZABKA Z4103 K1 MORZOW PL "; P�atno�� kart� 07.09.2013 Nr karty 5253xx4968;'3235031/19730 ';;TR.KART -2.79  ;'201325297301627796';-2,79;PLN;;;;;;;;;;;
        |2013-09-07;2013-09-09;"ZABKA Z4103 K1 MORZOW PL "; P�atno�� kart� 07.09.2013 Nr karty 5253xx4968;'3235031/19730 ';;TR.KART -5.08  ;'201325297301627793';-5,08;PLN;;;;;;;;;;;
        |2013-09-07;2013-09-09;"SKYCASH POLAND SA WARSZAWA PL "; P�atno�� kart� 07.09.2013 Nr karty 5253xx4968;'3235031/19730 ';;TR.KART -2.80  ;'201325297302648841';-2,80;PLN;;;;;;;;;;;
        |2013-09-07;2013-09-09;"FA LANGSTEINER KRAKOW PL "; P�atno�� kart� 07.09.2013 Nr karty 5253xx4968;'3235031/19730 ';;TR.KART -19.90  ;'201325297303190647';-19,90;PLN;;;;;;;;;;;
        |2013-09-07;2013-09-09;"SKYCASH POLAND SA WARSZAWA PL "; P�atno�� kart� 07.09.2013 Nr karty 5253xx4968;'3235031/19730 ';;TR.KART -2.80  ;'201325297302659306';-2,80;PLN;;;;;;;;;;;
        |2013-09-07;2013-09-07;"HA�A MICHA�, MACKIEWICZA 12/35 42-530 MORZ�W "; przelew Smart Saver P�atno�� kart� 05.09.2013 5,00 USD Kwota: 17,86 PLN;'54345013021000009180047558 ';ING Bank �l�ski S.A.; ;'201325064008163266';-0,89;PLN;;;;;;;;;;;
    """.trimMargin()


  val expected =
      """
      |Date       , Payee                                             , Category                                                                , Memo , Outflow , Inflow
      |08/09/2013 , "PAYPAL SPOTIFY 35314369001 GB "                  , P�atno�� kart� 08.09.2013 Nr karty 5253xx4968                           ,      , 19.99   , 0
      |08/09/2013 , "FIRMA ROGALA SP Z OO MLOSZOWA PL "               , P�atno�� kart� 08.09.2013 Nr karty 5253xx4968                           ,      , 16.67   , 0
      |07/09/2013 , "ZABKA Z4103 K1 MORZOW PL "                       , P�atno�� kart� 07.09.2013 Nr karty 5253xx4968                           ,      , 2.79    , 0
      |07/09/2013 , "ZABKA Z4103 K1 MORZOW PL "                       , P�atno�� kart� 07.09.2013 Nr karty 5253xx4968                           ,      , 5.08    , 0
      |07/09/2013 , "SKYCASH POLAND SA WARSZAWA PL "                  , P�atno�� kart� 07.09.2013 Nr karty 5253xx4968                           ,      , 2.80    , 0
      |07/09/2013 , "FA LANGSTEINER KRAKOW PL "                       , P�atno�� kart� 07.09.2013 Nr karty 5253xx4968                           ,      , 19.90   , 0
      |07/09/2013 , "SKYCASH POLAND SA WARSZAWA PL "                  , P�atno�� kart� 07.09.2013 Nr karty 5253xx4968                           ,      , 2.80    , 0
      |07/09/2013 , "HA�A MICHA�; MACKIEWICZA 12/35 42-530 MORZ�W "   , przelew Smart Saver P�atno�� kart� 05.09.2013 5;00 USD Kwota: 17;86 PLN   ,      , 0.89    , 0
      """
          .trimMargin()
          .replace("""(\\t| )*?,(\\t| )*?""".toRegex(), ",")

  @Test
  fun convertTest() {
    val converter = Converter(Config(emptyMap(), ";"))

    assertEquals(
        converter.convert(text),
        expected
    )

  }

}