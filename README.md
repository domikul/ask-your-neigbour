# ask-your-neigbour

Aplikacja AskYourNeigbour ma na celu ułatwienie lokalnym rolnikom/ogrodnikom oferowanie swoich produktów. Osoba zainteresowana umieszczeniem oferty o produkcie wypełnia formularz zgłoszeniowy w aplikacji. W formularzu musi zawrzeć dane o lokalizacji oferowanych produków oraz ich odpowiedni opis i cenę sprzedaży, wraz z telefonem kontaktowym dla pontencjalnych nabywców. Po wypełnieniu formularza na mapie znajdującej się na stronie pojawia się pinezka w miejscu adresu oferowanego produktu. Po kliknięciu w pinezkę wyświetlone zostają szczegóły oferty. Ze względu na sezonowość wielu produktów każda ofera jest limitowana czasowo (osoba zgłaszająca produkt określa czas ważności oferty). Po upływie tego czasu pinezka znika z mapy.
Do aplikacji dołączony został formularz rejestracji, który sprawdza czy wprowadzone hasło znajduje się wśród 50 najpopularniejszych haseł. Jeśli taka sytuacja wystąpi, użytkownik zostanie poindormowany o fakcie, że jego hasło jest zbyt słabe. Aplikację można rozwijać dalej, przykładowo o panel administratora, który będzie weryfikował poprawność danych wprowadzanych w formularzu. 

Przykład działania aplikacji: 

![alt text](https://i.ibb.co/TTTdC7c/1.png)
![alt text](https://i.ibb.co/pZTFt26/2.png)


# Stack technologiczny:

1. Backend: Spring Boot + Java 
2. Frontend: Angular 
3. Skrypt sprawdzający hasła: Python
4. Baza danych: H2 (open source)

# Sposób uruchomienia - backend

1. Zainstaluj javę w wersji 11
2. Sklonuj repozytorium
3. Otwórz w intellij idea
4. Prawym przyciskiem myszy kliknij na build.gradle -> link gradle project
5. Zbuduj i uruchom projekt

# Sposób uruchomienia - skrypt sprawdzający hasła

1. Zainstaluj python w wersji 3 
2. Sklonuj repozytorium 
3. W konsoli uruchom kolejno komendy: 
   pip install flask
   export FLASK_ENV=development
   export FLASK_APP=app
   python -m flask run

# Sposób uruchomienia - frontend 

1. Zainstaluj nodejs 16.13 lts
2. Sklonuj repozytorium
3. W folderze głównym części frontendowej aplikacji uruchom terminal
4. Wpisz kolejno polecenia:
   npm install - g @angular/cli
   npm install
   ng serve
5. W przeglądarce przejdź do adresu http://localhost:4200/
