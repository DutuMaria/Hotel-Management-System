# :bellhop_bell: Hotel Management System

## About
This project implements the basic necessities of a real hotel => managing customers, rooms, bookings, payments and reviews.

### Functionalities:

|                       AdminService                        |           CustomerService           |
|:---------------------------------------------------------:|:-----------------------------------:|
|                           logIn                           |                logIn                |
|                          logOut                           |               logOut                |    
|                    showFunctionalities                    |         showFunctionalities         |
|                        addCustomer                        |             viewProfile             |
|                          addRoom                          |          viewHotelServices          |
|                      viewAllBooking                       |            createBooking            |
|                viewBookingsForAGivenPeriod                |             payBooking              | 
|                      viewAllPayments                      | changePassword (update - customers) | 
|                    viewUnpaidPayments                     | changeUsername (update - customers) |
|  viewAllRooms (select * 2 - premiumRooms, standardRooms)  |             reviewHotel             |
|           viewAllCustomers (select - customers)           |              checkOut               |
|             viewAllReviews (select - reviews)             |   deleteReview (delete - reviews)   |
|                     changeRoomStatus                      |   updateReview (update - reviews)   |
| changeRoomType (update * 2 - premiumRooms, standardRooms) |                                     |
|   deleteRoom (delete * 2 - premiumRooms, standardRooms)   |                                     |
|            deleteCustomer (delete - customers)            |                                     |

#### LogIn
![](/img/login.png)

#### Admin menu
![](/img/AdminMenu.png)

#### Customer menu
![](/img/CustomerMenu.png)


------------------------------
    - changeRoomStatus (UNAVAILABLE <-> AVAILABLE)
    - changeRoomType (Single <-> Double)
    - checkOut (unavailable room => available room)
    - checkIn // TODO?


### Objects:
1) Hotel
2) Customer
3) Admin
4) Review
5) Booking
6) Payment
7) StandardRoom
8) PremiumRoom

--------------------------------
    - Hotel => singleton
        -> HotelServices (enum)
    - User => abstract class
        -> Customer => extends User
        -> Admin => singleton => extends User
        -> UserDocument (enum)
    - Review
    - Booking
    - Payment
        -> PaymentMethod (enum)
        -> PaymentStatus (enum)
    - Room => abstract class
        -> StandardRoom => extends Room
        -> PremiumRoom => extends Room
        -> RoomType (enum)
        -> RoomStatus (enum)
        -> RoomTypeComparator => implements Comparator

    - AdminService => singleton => implements ServiceInterface
    - CustomerService => singleton => implements ServiceInterface
    - ServiceInterface (interface)
    - Main


## Cerin??e:
Fiecare student va lucra la un proiect individual. Proiectul este structurat ??n mai multe etape.
Condi??ia de punctare a proiectelor:
- s?? nu prezinte erori de compilare
- s?? se implementeze cerin??ele date


### Etapa I (29 Martie):
1. Definirea sistemului:
    -  [x] S?? se creeze o lista pe baza temei alese cu cel pu??in 10 ac??iuni/interog??ri care
       se pot face ??n cadrul sistemului ??i o lista cu cel pu??in 8 tipuri de obiecte.
2.  Implementare: S?? se implementeze ??n limbajul Java o aplica??ie pe baza celor definite la primul punct. Aplica??ia va con??ine:
    - [x] Clase simple cu atribute private / protected ??i metode de acces
    - [x] Cel pu??in 2 colec??ii diferite capabile s?? gestioneze obiectele definite anterior (List, Set, Map, etc.) dintre care cel pu??in una sa fie sortata
    - [x] Se vor folosi array-uri uni/bidimensionale in cazul in care nu se parcurg colectiile pana la data checkpoint-ului.
    - [x] Utilizare mo??tenire pentru crearea de clase adi??ionale ??i utilizarea lor ??n cadrul colec??iilor;
    - [x] Cel pu??in o clasa serviciu care sa expun?? opera??iile sistemului
    - [x] O clasa Main din care sunt f??cute apeluri c??tre servicii


Clasele pentru care am facut CSV 
- Review
- Customer
- PremiumRoom
- StandardRoom

### Etapa II (3 Mai):

1. Extinde??i proiectul din prima etap?? prin realizarea persisten??ei utiliz??nd fi??iere.
    - [x] Se vor realiza fi??iere de tip CSV pentru cel pu??in 4 dintre clasele definite ??n prima etap??. Fiecare coloana din fi??ier este separata de virgula. Exemplu:nume,prenume,varsta
    - [x] Se vor realiza servicii singleton pentru scrierea ??i citirea din fi??iere
    - [x] Folosire generice pentru scriere si citire fisiere
    - [x] La pornirea programului se vor ??nc??rca datele din fi??iere utiliz??nd serviciile create
    - [x] Scriere in fisiere in timpul rularii aplicatiei
2. Realizarea unui serviciu de audit
    - [x] Se va realiza un serviciu care s?? scrie ??ntr-un fi??ier de tip CSV de fiecare dat?? c??nd este executata una dintre ac??iunile descrise ??n prima etap??. Structura fi??ierului: nume_actiune, timestamp
3. In plus
   - [x] Utilizare streams + lambda
   - [x] Utilizare exceptii
   - [x] Utilizare colectii in loc de array-uri


### Etapa III (31 Mai):
- [x] ??nlocui??i serviciile realizate ??n etap?? a II-a cu servicii care s?? asigure persisten??a utiliz??nd baza de date folosind JDBC.
- [x] S?? se realizeze servicii care s?? expun?? opera??ii de tip create, read, update, delete pentru cel pu??in 4 dintre clasele definite.

Clasele pentru care am facut tabele in baza de date
- Review
- Customer
- PremiumRoom
- StandardRoom