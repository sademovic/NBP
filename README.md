# NBP

#Opis teme 

U okviru ovog kursa razvijat ćemo aplikaciju koja omogućava veoma jednostavnu kupovinu, odnosno prodaju artikala na web stranici. Svako ko pristupi stranici može pretraživati artikle koji su objavljeni za prodaju. Sa druge strane,  samo registrovani korisnici mogu objavljivati artikle spremne za prodaju. Zainteresovani posjetitelji stranice, moći će kontaktirati prodavca na više načina kao što su: pozivom na kontakt broj, slanjem poruke, slanjem maila na adresu koju je prodavac iskoristio za registraciju na stranicu i sl.

#Funkcionalnosti

Online shop „ProdajIKupi“ sadrži sljedeće funkcionalnosti:
1.	Kreiranje korisničkog računa - Korisnik može kreirati račun ako želi da objavi artikal za prodaju.
2.	Prijava na web stranicu - Pomoću korisničkog imena i šifre, korisnik se može prijaviti.
3.	Izmjena podataka na računu - Korisnik ima mogućnost izmjene informacija o sebi kao što su ime, prezime, šifra i sl.
4.	Objavljivanje artikala na stranici - Registrovani korisnici mogu objaviti artikal na stranici.
5.	Pregled/Uređivanje svih objavljenih aritakala - Sve artikle koje je objavio, korsinik može pregledati ili urediti (izmjeniti informacije o artiklima).
6.	Pretraživanje artikala - Korisnici mogu pretraživati artikle kako po nazivu tako i korištenjem filtera. (cijena, kategorija, lokacija)
7.	Slanje poruke/email - Ako je korisnik zainteresovan za određeni artikal, moguće je poslati poruku kroz web stranicu prodavaocu tog artikla.
8.	Brisanje artikala - U slučaju da je artikal prodat van okvira stranice ili je prodavac odlučio da ne želi prodati artikal, moguće ga je obrisati sa web stranice.
9.	Obavljanje transakcije - Moguće je izvršiti „transakciju“ preko web stranice, od slanja zahtjeva za transakcijom do potvrđivanja da je transakcija obavljenja. Transakcija se odnosi na zahtjev za kupovinom artikla, gdje prodavaocu dolazi poruka o zahtjevu za kupovinom, te on može završiti kupovinu ili odbiti zahtjev.
10.	Arhiviraj moje artikle - Svi artikli koji se prodaju dok ih je korisnik imao kao sačuvane ili artikli koje je korisnik obrisao se arhiviraju.
11.	Čitanje/pregled poruka - Korisnik može pročitati sve poruke odnosno pregledati sve konverzacije sa ostalim korisnicima.
12.	Pregled transakcija - Korisnik može pregledati sve transakcije u kojima učestvuje (sve transakcije koje prodaje odnosno kupuje). 
13.	Admin može da kreira, uređuje i briše kategorije i podkategorije.
14.	Admin može da pregleda ili brisati logove sa svih servisa.
15.	15.	Admin može da pregleda statističke podatka sa servisa, kao sto su broj prodanih arikala za odabrani dan, broj objavljenih artikala za odabrani dan, broj aktivnih artikala iz odabrane kategorije.



#Projekat se sastoji od 5 mikroservisa

1.	UserService
2.	ItemsService
3.	MessageService
4.	TransactionService
5.	LogService
6.	AuthService


#Tehnološki stack

Za back-end dio ce se koristiti Spring Boot, za discovery server Eureka, za API getaway Zuul, te za front-end Angular. Koristi cemo relacionu bazu podataka PostgreSQL.

Za pokretanje potrebno je da su portovi 8081, 8082, 8083, 8084, 8085, 8086 i 8080 slobodni, te 8761 za eureku. Na portu 8081 je user-service. Na portu 8082 je items-service. Na portu 8083 je message-service. Na portu 8084 je transaction-service. Na portu 8085 je auth-service. Na portu 8086 je log-service. Na portu 8080 je zuul api getaway.

