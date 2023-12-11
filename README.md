# Reparaturservice

Einer unserer Kunden betreibt eine Autowerkstatt. Auf seiner Homepage möchte er automatisierte Kostenvoranschläge für typische Reparaturen einbauen. Er hat bereits folgende Wünsche geäußert:

1. Meine Kunden sollen ihr Automodell und eine Liste von benötigten Reparaturen angeben können. Für die Kostenvoranschläge muss ich immer in der Tabelle price_estimations.csv nachsehen, welche Kosten im Durchschnitt erwartet werden. Das soll automatisch berechnet und dem Kunden angezeigt werden.
2. Ist der Kunde mit dem Kostenvoranschlag einverstanden, soll er direkt auf der Homepage einen Terminvorschlag zur Reparatur angeben können. Schickt der Kunde den Terminwunsch ab, sollen Terminwunsch, Kundennummer, Modell, Kennzeichen und die Liste an benötigten Reparaturen direkt an das Verwaltungssystem geschickt werden. Dort wird es dann bestätigt und an den Kunden verschickt.
3. Eine Authentifizierung soll nicht erfolgen. 

Das Verwaltungssystem ist im internen Netz des Kunden per REST Call erreichbar und die Schnittstellenspezifikation wurde uns als OpenAPI zur Verfügung gestellt. Auch die Preisliste haben wir bereits erhalten und beide Dateien sind im Projekt als Ressourcen abgelegt.

# Details zum Build

Java Spring Boot Applikation, verwaltet durch Maven. Empfohlene Versionen: Java 17.0.7, Maven 3.6.3

# Aufgaben

1. Ihr Kollege hat bereits mit der Umsetzung begonnen. Er hat einen REST Call implementiert und bittet Sie um einen Code Review. Die Änderungen liegen auf dem Branch feature/send-repair-request-to-administration. Welches Feedback geben Sie ihm?
2. Erweitern Sie den Code, um die gesamte Kundenanforderung zu erfüllen.