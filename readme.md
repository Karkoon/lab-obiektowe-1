### Lab 3

Odpowiedź na zadanie 10.

Wydaje mi się, że istnieją 3 możliwości, które się same nasuwają.

- stworzenie tablicy N x N, która w komórce, w której znajduje się zwierzę/obiekt
 przechowywałaby referencję do niego i gdy jakiś inny obiekt chciałby zająć tę pozycję
 to sprawdzałby czy miejsce [x][y] ma w sobie wartość null. Jeśli tak to mógłby się tam
 wpisać. 
 \
 Cechy:
    - szybkość dostępu po samych koordynatach
    - prostota "modelu"
    - powinno zajmować dość dużo pamięci dla dużych map (referencje do null)
    - ilość zwierząt nie ma znaczenia
    - ustalona wielkość świata
    
- użycie listy i porównywanie pozycji uprzednio zapisanych zwierząt/obiektów z pozycją
 na którą obecnie chcemy coś wstawić.
 \
 Cechy: 
    - wolniejsze od bezpośredniego dostępu
    - kolejny prosty sposób
    - zajmuje mniej pamięci (wewnętrzna tablica jest długości zależnej od liczby zwierząt,
    w teorii może być tyle zwierząt co pól w świecie, w praktyce może być różnie)
    - okej dla małej ilości zwierząt
    - dynamiczna/nieograniczona wielkość świata

- użycie HashMapy z Vector2d jako kluczem i zwierzęciem/objektem jako kluczem.
  \
  Cechy:
  - "bezpośredni" dostęp po koordynatach (narzut z hashowania)
  - nadal używa dość podstawowej klasy Javy
  - powinno nie zajmować tyle pamięci co tablica N x N (ale HashMapa nadal potrzebuje
  w swojej implementacji jakiejś tablicy, dodatkowo klucze i wartości są z tego co mi się
  wydaje pakowane nowymi obiektami, więc to jest dodatkowy narzut pamięciowy, który może
  niwelować zalety)
  - ilość zwierząt nie ma znaczenia
  - dynamiczna/nieograniczona wielkość świata
    
- Spotkałem się też ze strukturą KD-tree, ale mało o niej wiem. Podobno jest przydatna przy dalszym
implementowaniu jakichś funkcji, które polegają na znajdowaniu najbliższego sąsiada albo czegoś takiego.

- Kolejnym sposobem byłoby użycie klasy BitSet z wartościami True/False dla indeksów
w zlinearyzowanej postaci dla wyimaginowanej tablicy pozycji [x][y], w którym zaznaczalibyśmy
czy pozycja jest zajęta bądź nie. 

Jeśli nie ma się konkretnej intuicji, która pozwoliłaby na wybór odpowiedniego sposobu
powinno użyć się profilera dla każdej implementacji i spróbować dopasować to co faktycznie
jest najlepsze. Na pewno nie wiem kiedy (od jakiej liczby zwierząt) tablica staje się pamięciowo
bardziej optymalna od HashMapy czy ArrayListy i to musiałbym sprawdzić.
 
Jednak wydaje mi się, że stawiałbym na BitSet, bo oprócz ograniczenia wielkości świata 
(a wielkość jest ustalona i nie ma wspomnienia czy ma być nieograniczona, więc to jest
rozwiązywanie problemu, który po prostu nie został nawet postawiony)
to raczej jest to też najbardziej optymalny pamięciowo sposób.

Trzeba też w sumie wziąć pod uwagę kwestię czytelności kodu, ale to nie wiem. Pewnie fasada
się nada i będzie okej.
