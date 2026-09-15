
# ASCII Art Generator
 
**Zamień dowolny obraz w ASCII art wprost w terminalu.**
Zero zależności zewnętrznych — tylko czysta Java (`javax.imageio` + `java.awt`).

</div>
---
 
## Podgląd
 
<div align="center">
![demo](assets/cegielka.png)
 
<sub>pozdrowienia dla kumatych XD</sub>
 
</div>
## Dlaczego to jest fajne
 
-  **Zero zależności** — sam JDK, żadnego Mavena, Gradle'a, żadnych `.jar`-ów
-  **Tryb kolorowy** — pełny ANSI truecolor, obraz w terminalu wygląda jak mały pixel art
-  **Szybkie** — kilkadziesiąt linii kodu, konwersja w ułamku sekundy
-  **Sensowne błędy** — jak coś pójdzie nie tak, dostajesz konkretny powód, a nie tajemnicze `null`
## Instalacja
 
```bash
git clone git@github.com:MrCarrot102/AsciiMore.git
cd ascii-art-java
javac src/AsciiArt.java -d out
```
 
Wymagania: JDK 11+ (testowane na JDK 21).
 
##  Użycie
 
```bash
java -cp out AsciiArt <plik_obrazu> [szerokość] [--color] [--invert]
```
 
| Argument      | Opis                                                                 |
|---------------|-----------------------------------------------------------------------|
| `<plik>`      | Ścieżka do obrazu — wymagane                                          |
| `<szerokość>` | Szerokość w znakach, domyślnie `100`                                  |
| `--color`     | Kolorowy output (ANSI truecolor)                                      |
| `--invert`    | Odwraca jasność — przydatne dla ciemnych obrazów na jasnym tle        |
 
### Przykłady
 
```bash
# Podstawowe użycie
java -cp out AsciiArt cegla.png
 
# Kolorowo i szerzej
java -cp out AsciiArt cegla.png 150 --color
 
# Odwrócona jasność
java -cp out AsciiArt cegla.png 80 --invert
```
 
## Jak to działa
 
```
  obraz wejściowy
        │
        ▼
  skalowanie do N znaków szerokości
  (z korektą proporcji — znak w terminalu
   jest ~2× wyższy niż szerszy)
        │
        ▼
  dla każdego piksela:
  luminancja = 0.2126·R + 0.7152·G + 0.0722·B
        │
        ▼
  mapowanie na znak z rampy
  "@%#*+=-:. "  (ciemny → jasny)
        │
        ▼
  (opcjonalnie) kolorowanie znaku
  kolorem oryginalnego piksela (ANSI truecolor)
        │
        ▼
     ASCII art w terminalu
```
 
## Obsługiwane formaty
 
Wszystko, co obsługuje wbudowany `ImageIO`: **PNG, JPG/JPEG, BMP, GIF, WBMP, TIFF**.
Nie obsługuje natywnie **WebP, HEIC ani AVIF** — takie pliki trzeba najpierw przekonwertować.