Raportti projektin kulusta
==========================

Projektiin osallistuneet henkilöt
---------------------------------

Hannes Ihalainen, Jesper Kuutti, Veikko Laine, Anna Piirnainen, Jussi 
Steenari

Kohdattuja ongelmia
-------------------
"kerrataan jokaisen sprintin aikana kohdatut ongelmat (prosessiin-, projektityöskentelyyn- ja teknisiin asioihin liittyvät)"
Siis jokaiselle sprintille erikseen kaikki kohdat...? Kenties
### Sprintti 1

Oli hieman epäselvyyksiä product backlogin hallinnoimisesta: emme käsittäneet että sen priorisointikin kuului vastuullemme. Sen alkuperäisen muodostamisen jälkeen emme olleet juuri ylläpitäneet, tarkentaneet, tai laajentaneet sitä ennen ensimmäistä asiakastapaamista. Käytimme kenties turhan paljon aikaan yksinkertaisten tapausten hiomiseen - ei ehkä ole tarpeen testata aivan ensimmäisiä tekstikäyttöliittymiä hyvin tarkkaan, koska ne tulevat muuttujaan joka tapauksessa niin paljon läpi projektin. Ominaisuusbranchit osoittivat hieman ongelmia, koska jokainen oli branchatty hieman erisisältöisestä masterista. Melko loppuvaiheessa tapahtunut kaiken mergeäminen master-haaraan aiheutti konflikteja, joiden ratkaisemiseen meni ennakoimattoman paljon aikaa. Huomasimme myös että joissain kohdissa uutta koodia ylikirjoitettiin vanhemmilla versiolla, ja tämä piti korjata erikseen. 

Ryhmän sisäistä kommunikaatiota ei tapahtunut merkittävästi ennen kuin lähellä asiakaspalaveria. Tämän vuoksi asiakastapaamisen jälkeen loimmekin slack-työtilan tiheämpää kommunikaatiota varten. 

### Sprintti 2 

Jonkin verran haasteita tietokannan käyttöönottamisessa. Ainakin tagien tallentaminen omana tauluna niin, että kirjanmerkin tagit haetaan ja tallennetaan tietokantaan automaattisesti kirjanmerkkiä muokatessa, aiheutti aluksi ongelman. Lopullinen ratkaisu ei skaalaudu suuremmille datamäärille, sillä tagit haetaan tietokannasta heti kun vastaava kirjanmerkkikin. Mahdolliseksi uhaksi oli tulossa varsin rakenneton ohjelma, mutta kesken sprintin lisäsimme uusia luokkia joiden vastuulla oli täysin tietynlaisten tietokantayhteyksien hallinnointi. 

Projektityöskentelyssä kevyeksi pullonkaulaksi muodostui tietokannan käyttöönottaminen, sillä tämä oli tehtävä ennen kuin muita ominaisuuksia oli järkevää alkaa toteuttaa. Hyvänä asiana tietokanta saatiin käyttöön työviikon alkupuolella, ja käyttämisen tarkempi hiominen ja muiden käyttäjätarinoiden toteuttaminen pystyivät etenemään samanaikaisesti loppusprintin ajan.


### Sprintti 3

### Sprintti 4


Mikä sujui hyvin
----------------

Sprinttisuunnittelussa ei ahnehdittu liikaa user storyjä, ja useimmissa tapauksissa sprinttiin valitut ominaisuudet saatiin toteutettua suunnitellussa sprintissä. Kukaan ei ahnehtinut kaikkea tehtäväkseen, vaan pyrittiin neuvottelemaan ja jakamaan tehtävää tiimin kesken. Aikatauluissa ja muissa ryhmän sovituissa normeissa pitäydyttiin oikein hyvin. 

Parannuskohteita
----------------
Kenties aktiivisempi vuorovaikutus ja suunnittelu - nyt suunnittelupäätökset saattoivat jäädä toteuttajan tehtäväksi. Tätä luonnollisesti selittää yhteisen työskentelytilan ja vakituisten aikoijen puuttumien. Daily scrum ja fyysisesti samassa tilassa tapahtuva kommunikaatio helpottaisi yhteistä miettimistä ja tiedon välittämistä huomattavasti, ja kenties olisi ollut hyvä idea yrittää löytää esimerkiksi yhteisiä aikoja kun kaikki tai osa porukasta on yliopistolla samaan aikaan.


Oppimisesta
-----------

Opimme miniprojektista miten Scrum-kehittäjätiimi voi käytännössä toimia, ja minkälaisia onglemia se voi kohdata. Osalle tämä projekti oli ensimmäinen hieman isomman ryhmän ohjelmointiryhmän yhteisprojekti, ja projektin kuluessa oppi siihen, että valtaosa koodista ei ole omaa käsialaa. Tästä konkretisoitui miksi kaikkien yhdessä sopima definition of done ja koodistandardit ovat tärkeitä. Tällöin tehtävien keskinäisiin jakamisiin ei mene niin merkittävää työpanosta, ja tämän voi käyttää lopputuotteelle arvokkaampaan työhön.

`Mitä olisi halunnut oppia?`

`Mikä tuntui turhalta?`