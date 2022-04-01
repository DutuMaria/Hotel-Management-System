# :bellhop_bell: Hotel Management System

## About
This project implements the basics necessities of a real hotel, managing customers, rooms, bookings, payments and reviews.

### Functionalities:

|    AdminService     |   CustomerService   |
|:-------------------:|:-------------------:|
|        logIn        |        logIn        |
|       logOut        |       logOut        |    
| showFunctionalities | showFunctionalities |

|      Admin       |     Customer      |
|:----------------:|:-----------------:|
|   addCustomer    |    viewProfile    |
|     addRoom      | viewHotelServices |
| viewAllBookings  |   createBooking   |
| viewAllPayments  |    payBooking     | 
|   viewAllRooms   |  changePassword   | 
| viewAllCustomers |  changeUsername   |
| changeRoomStatus |    reviewHotel    |
|  changeRoomType  |     checkOut      |
|    deleteRoom    |                   |

#### LogIn
![](/img/login.png)

#### Admin menu
![](/img/menuAdmin.png)

#### Customer menu
![](/img/menuCustomer.png)


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