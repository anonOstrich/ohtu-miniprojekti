Raportti projektin kulusta
==========================

Projektiin osallistuneet henkilöt
---------------------------------

Hannes Ihalainen, Jesper Kuutti, Veikko Laine, Anna Piirainen, Jussi 
Steenari

Kohdattuja ongelmia
-------------------

### Sprintti 1

Oli hieman epäselvyyksiä product backlogin hallinnoimisesta: emme käsittäneet että sen priorisointikin kuului vastuullemme. Sen alkuperäisen muodostamisen jälkeen emme olleet juuri ylläpitäneet, tarkentaneet, tai laajentaneet sitä ennen ensimmäistä asiakastapaamista. Käytimme kenties turhan paljon aikaa yksinkertaisten tapausten hiomiseen - ei ehkä ole tarpeen testata aivan ensimmäisiä tekstikäyttöliittymiä hyvin tarkkaan, koska ne tulevat muuttumaan joka tapauksessa niin paljon läpi projektin. 

Työjärjestys monen henkilön projektissa osoittautui aluksi GitHubin kanssa aavistuksen haasteelliseksi. Ominaisuusbranchit oli branchatty hieman erisisältöisestä masterista, ja melko loppuvaiheessa tapahtunut kaiken mergeäminen master-haaraan aiheutti konflikteja, joiden ratkaisemiseen meni ennakoimattoman paljon aikaa. Huomasimme myös että joissain kohdissa uutta koodia ylikirjoitettiin vanhemmilla versiolla, ja tämä piti korjata erikseen. 

Ryhmän sisäistä kommunikaatiota ei tapahtunut merkittävästi ennen kuin lähellä asiakaspalaveria. Tämän vuoksi asiakastapaamisen jälkeen loimmekin Slack-työtilan tiheämpää kommunikaatiota varten. 

### Sprintti 2 

Jonkin verran haasteita tietokannan käyttöönottamisessa. Ainakin tagien tallentaminen omana tauluna niin, että kirjanmerkin tagit haetaan ja tallennetaan tietokantaan automaattisesti kirjanmerkkiä muokatessa, aiheutti aluksi ongelmia. Lopullinen ratkaisu ei skaalaudu suuremmille datamäärille, sillä tagit haetaan tietokannasta heti kun vastaava kirjanmerkkikin. Mahdolliseksi uhaksi oli tulossa varsin rakenteeton ohjelma, mutta kesken sprintin lisäsimme uusia luokkia joiden vastuulla oli täysin tietynlaisten tietokantayhteyksien hallinnointi. 

Projektityöskentelyssä kevyeksi pullonkaulaksi muodostui tietokannan käyttöönottaminen, sillä tämä oli tehtävä ennen kuin muita ominaisuuksia oli järkevää alkaa toteuttaa. Hyvänä asiana tietokanta saatiin käyttöön työviikon alkupuolella, ja käyttämisen tarkempi hiominen ja muiden käyttäjätarinoiden toteuttaminen pystyivät etenemään samanaikaisesti loppusprintin ajan.


### Sprintti 3

Teknisiä ongelmia kohdattiin jälleen ainakin tietokannanhallinnan kanssa. Halusimme täyttää tietokannan järkevällä alkudatalla, jos tietokantaa ei ole ohjelman käynnistyessä olemassa. Perustapauksessa tämä onnistui melko helposti lukemalla insert-lausekkeet erillisestä .sql-tiedostosta, mutta lisäys-ja muokkausaikojen automaattinen lisäys näin luotuihin olioihin ei onnistunut aivan vaivatta. Aiemmassa ratkaisussa kyseiset kentät generoitiin automaattisesti, kun Java-olio tallennettiin tietokantaan; tässä tapauksessa taas tietokannassa suoritettiin suoraan SQL-kutsuja, joten tämä automatisointi tehtiin tietokannanhallintajärjestelmän tasolla. Ongelmaan perehtymällä ja sopivalla googlaamisella löytyi lopulta onneksi toimiva ratkaisu. 

Tällä viikolla huomasimme myös, että luokan ominaisuuksien vähentäminen ja refaktoroidut luokat aiheuttivat melko paljon vaivaa olemassaolevien testien kannalta. Olimme aiemmin jo ennakoineet monimutkaisia kirjanmerkkejä ja suuren määrän oleellisia kenttiä, mutta asiakkaan palautteen jälkeen tällä viikkolla vähensimme niiden lukumäärää ja tarvittavia tietoja. Olemassaolevat testit ja testien käyttämät tekstitiedostot vaativat paljon muokkaamista. Kenties tämä olisi voitu välttää toteuttamalla aluksi vain hyvin yksinkertaiset kirjanmerkkiluokat, ja lähteä tarkentamaan niitä vasta asiakastapaamisessä saatujen ideoiden pohjalta. 

### Sprintti 4

Merge-konflikteja oli jonkin verran, mutta niiden ratkaiseminen oli huomattavasti nopeampaa kuin aiempina viikkoina. Ryhmätyössä ei kohdattu juuri ongelmia tämän sprintin aikanaa. Sprintin aikana laajennettiin jo olemassa olevia ominaisuuksia sekä refaktoroitiin koodia.  


Mikä sujui hyvin
----------------

Sprinttisuunnittelussa ei ahnehdittu liikaa user storyjä, ja useimmissa tapauksissa sprinttiin valitut ominaisuudet saatiin toteutettua suunnitellussa sprintissä. Kukaan ei ahnehtinut kaikkea tehtäväkseen, vaan pyrittiin neuvottelemaan ja jakamaan tehtävää tiimin kesken. Tätä edesautti se, että User Storyt oli jaettu helposti toteutettaviin paloihin. Aikatauluissa ja muissa ryhmän sovituissa normeissa pitäydyttiin oikein hyvin. 

Parannuskohteita
----------------
Kenties aktiivisempi vuorovaikutus ja suunnittelu - nyt suunnittelupäätökset saattoivat jäädä toteuttajan tehtäväksi. Tätä luonnollisesti selittää yhteisen työskentelytilan ja vakituisten aikojen puuttumien. Daily scrum ja fyysisesti samassa tilassa tapahtuva kommunikaatio helpottaisi yhteistä miettimistä ja tiedon välittämistä huomattavasti, ja kenties olisi ollut hyvä idea yrittää löytää esimerkiksi yhteisiä aikoja silloin kun kaikki tai osa porukasta on yliopistolla samaan aikaan. Oli hyvä kokeilla, miten ohjelmiston tekeminen toimii hyvin kevyellä suunnittelu- ja hallintaprosessilla, mutta pieni lisäys suunnitteluun ja koordinointiin käytetyssä ajassa olisi tässä tapauksessa ehkä vähentänyt ylimääräistä työtä myöhemmissä vaiheissa projektia.  


Oppimisesta
-----------

Opimme miniprojektista miten Scrum-kehittäjätiimi voi käytännössä toimia, ja minkälaisia onglemia se voi kohdata. Osalle tämä projekti oli ensimmäinen hieman isomman ryhmän ohjelmointiryhmän yhteisprojekti, ja projektin kuluessa oppi siihen, että valtaosa koodista ei ole omaa käsialaa. Tästä konkretisoitui miksi kaikkien yhdessä sopima definition of done ja koodistandardit ovat tärkeitä. Tällöin tehtävien keskinäisiin jakamisiin ei mene niin merkittävää työpanosta, ja tämän voi käyttää lopputuotteelle arvokkaampaan työhön.

Mitä olisi halunnut oppia?
-----------------------
Ohjelmistotuotantokurssin sisältö tarjosi tarvittat työvälineet miniprojektin toteuttamiseen. Oikeastaan ainoa asia, mitä olisi voinut vielä painottaa olisi ollut parhaat käytänteet liittyen erilaisiin kommunikointikointitapoihin  projektityöskentelyssä. Neljän viikon rajallsesta ajasta olisi saanut ehkä enemmän irti jos alussa olisi mennyt vähemmän aikaa yrityksen ja erehdyksen kautta etenemiseen. 

Mikä tuntui turhalta?
-------------------

Yhden asiakastapaamisen yhteydessä asiakas esitti uusia vaatimuksia, jotka vaativat ohjelman rakenteen uudelleen kirjoittamista. Tällöin aiempi tehtyt työ tuntui vähän turhalta. Kuitenkin tämä tilanne oli hyvin opetuksellinen ja seuraavalla kerralla osaa paremmin varautua paremmin tällaisiin tilanteisiin.
