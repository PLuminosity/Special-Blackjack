# Special-Blackjack
**Speciální verze karetní hry Blackjack napsaná v Javě - projekt do předmětu programování**
## Jak program funguje
Tento program simuluje karetní hru Blackjack (česky: Jedenadvacet/Oko bere).
Po spuštění programu se hráč přihlašuje nebo registruje.
Hráč hraje proti počítačí (dealer) podle běžných pravidel Blackjacku.
Speciální část hry pochází v divokých kartách, které aktivují různé efekty při použití.
Hra probíhá v konzoli a uživatel zadává své volby pomocí klávesnice.
Výsledky hry jsou zapisovány do .csv souboru.
## Jak program spustit
### První varianta
1. Ujistěte se, že máte nainstalovaný a používáte [Java Development Kit](https://www.oracle.com/java/technologies/downloads/#jdk24-windows) (verze 22 nebo vyšší)
2. Stáhněte všechny .java soubory do jednoho adresáře
3. Otevřete si cmd/terminal v adresáři, který obsahuje .java soubory a zkompilujte pomocí:
```bash
javac *.java
```
4. Hru spustíte pomocí cmd příkazem:
```bash
java BlackjackGame
```
### Druhá varianta
1. Ujistěte se, že máte nainstalovaný [Java Development Kit](https://www.oracle.com/java/technologies/downloads/#jdk24-windows) (verze 22 nebo vyšší)
2. Stáhněte všechny .java soubory do jednoho adresáře
3. Otevřete si IDE (Intellij Idea)
4. Ujistěte se, že se používá správnou verzi Javy (Project Structure -> SDK -> verze 22 nebo výšší
5. Otevřete BlackjackGame => zkompilujte a spusťte (SHIFT+F10 nebo zelená šipka v pravo nahoře)
## Divoké karty
Ve hře se současně vyskytují tři divoké karty, které aktivují různé efekty. Divoké karty je možné dostat při rozdávání karet.
1. **Raise limit 24**
   - Efekt: zvýší limit současné hry z 21 na 24
2. **Add to dealer**
   - Efekt: přidá dealerovy náhodnou hodnotu od 2 do 5
3. **Reset hand**
   - Efekt: obnovení ruky hráče na původní stav
## Splněné části zadání
- [x] Práce s datovými typy
- [x] Práce s poli a kolekcemi
- [x] Objektově orientované programování
- [x] Práce se soubory nebo s databází
- [x] Ošetření výjimek
- [x] Uživatelské rozhraní
## Řešené problémy
- Řešení situace s esy (Ace) - mohou mít hodnotu 1 nebo 11
- Validace vstupů pro uživatele
- Při překročení hodnoty 21 hra skončí
- Hra pokračuje, když byla zahrána divoká karta Raise limit 24
- Pokud byla zahrána divoká karta Add to dealer, zvyší se hodnota dealorovy ruky
- Po konci každé hry (výhra nebo prohra) jsou zapsané správné data do souboru blackjack_log.csv
- Možnost dobít peníze při 0 Kč
- Ukládání stavu hráče do souboru players.txt (zůstatek při ukončení, uživatelské jméno a heslo pro přihlášení)
