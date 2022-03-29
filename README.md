# :bellhop_bell: Hotel Management System

### Functionalities:

- AdminService
    - logIn
    - logOut
    - showFunctionalities

- CustomerService
    - logIn
    - logOut
    - showFunctionalities

- Admin
    - addCustomer
    - addRoom
    - viewAllBookings
    - viewAllRooms
    - viewAllCustomers
    - chageRoomStatus (UNAVAILABLE <-> AVAILABLE)
    - changeRoomType (Single <-> Double)
    - deleteRoom
  --------------
    - based on dates //TODO

- Customer
    - createBooking
    - viewProfile
    - viewHotelServices
    - payBooking
    - editProfile => changePassword for now
    - checkOut (unavailable room => available room)
  --------------
    - check //TODO
    - deleteBooking //TODO

- Booking
    - calculatePayment

### Objects:
- Hotel => singleton
    - HotelServices (enum)
- User => abstract class
    - Customer
    - Admin => singleton
    - UserDocument (enum)
- Booking
- Payment
    - PaymentMethod (enum)
    - PaymentStatus (enum)
- Room
    - StandardRoom
    - PremiumRoom
    - RoomType (enum)
    - RoomStatus (enum)
    - RoomTypeComparator => implements Comparator
- AdminService => singleton => implements interfcae
- CustomerService => singleton => implements interface
- ServiceInterface (interface)
- Main
-------------
- Check //TODO
- Staff //TODO

## Cerințe:
Fiecare student va lucra la un proiect individual. Proiectul este structurat în mai multe etape.
Condiția de punctare a proiectelor:
- să nu prezinte erori de compilare
- să se implementeze cerințele date


### Etapa I (29 Martie):
1. Definirea sistemului:
    -  [x] Să se creeze o lista pe baza temei alese cu cel puțin 10 acțiuni/interogări care
       se pot face în cadrul sistemului și o lista cu cel puțin 8 tipuri de obiecte.
2.  Implementare: Să se implementeze în limbajul Java o aplicație pe baza celor definite la primul punct. Aplicația va conține:
    - [x] Clase simple cu atribute private / protected și metode de acces
    - [x] Cel puțin 2 colecții diferite capabile să gestioneze obiectele definite anterior (List, Set, Map, etc.) dintre care cel puțin una sa fie sortata
    - [x] Se vor folosi array-uri uni/bidimensionale in cazul in care nu se parcurg colectiile pana la data checkpoint-ului.
    - [x] Utilizare moștenire pentru crearea de clase adiționale și utilizarea lor în cadrul colecțiilor;
    - [x] Cel puțin o clasa serviciu care sa expună operațiile sistemului
    - [x] O clasa Main din care sunt făcute apeluri către servicii

### Etapa II (3 Mai):

1. Extindeți proiectul din prima etapă prin realizarea persistenței utilizând fișiere.
    - [ ] Se vor realiza fișiere de tip CSV pentru cel puțin 4 dintre clasele definite în prima etapă. Fiecare coloana din fișier este separata de virgula. Exemplu:nume,prenume,varsta
    - [ ] Se vor realiza servicii singleton generice pentru scrierea și citirea din fișiere
    - [ ] La pornirea programului se vor încărca datele din fișiere utilizând serviciile create
2. Realizarea unui serviciu de audit
    - [ ] Se va realiza un serviciu care să scrie într-un fișier de tip CSV de fiecare dată când este executata una dintre acțiunile descrise în prima etapă. Structura fișierului: nume_actiune, timestamp

### Etapa III (31 Mai):
- [ ] Înlocuiți serviciile realizate în etapă a II-a cu servicii care să asigure persistența utilizând baza de date folosind JDBC.
- [ ] Să se realizeze servicii care să expună operații de tip create, read, update, delete pentru cel puțin 4 dintre clasele definite.