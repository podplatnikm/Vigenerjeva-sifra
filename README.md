
# Vigenerjeva Šifra

Implementacija Vigenerjeve šifre za kriptiranje.
Zahteve:
* vnos besedila ali nalaganje datoteke za obdelavo,
* možnost kriptiranja in dekriptiranja za uporabnika,
* prikaz kriptiranega besedila,
* možnost za vnos ključa,
* v primeru, da uporabnik ne vnese ključa, kot ključ uporabite svoje ime,

Ker Vigenerjeva šifra kriptira samo črke, dodatno vključite še funkcionalnost:
* zamenjave števil za dolžino ključa (če je vaš ključ dolg 6 in če želite zakriptirati števko 2 je rezultat 8),
* kriptiranje naj poteka števko po števko, torej če imate v besedilu 2014, posebej zakriptirate 2, nato 0, itd.
* zamenjave števil izvedite smiselno (lahko si pomagate npr. s posebnimi znaki, 
če ima zakriptirano število 2 števki) oz. naredite enako kot pri kodiranju črk - 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 itd.

## Namestitev in zagon

Java program scompilate in zaženete
* Screenshot: http://shrani.si/f/2V/XT/mBUmQAv/screen.png
## Built With

* [JavaFX](https://docs.oracle.com/javafx/2/overview/jfxpub-overview.htm) - uporabljeno ogrodje
* [JFoenix](https://github.com/jfoenixadmin/JFoenix) - knjižnica za izgled
