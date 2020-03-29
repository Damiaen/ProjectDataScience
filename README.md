# ProjectDataScience
HBO-ICT jaar 2 periode 3 - Project: Big Movie

### Toevoegen van IMDB database
1 - Download alle database bestanden van https://datasets.imdbws.com/.  
2 - Maak een nieuw mapje "raw" aan (voor de raw data).  
3 - Plaats dit mapje met de IMDB database in src/main/resources/database  
4 - Unzip de database bestanden, zodat je deze kan gebruiken

### Parsen van de ruwe .tsv data naar .csv
1 - Open de applicatie nadat je de "raw" folder in "src/main/resources/database" hebt geplaatst.  
2 - Druk op de "home" pagina op de knop "Ruwe data converteren naar .csv".  
3 - Druk op de "Beginnen met Parsen" knop en wacht totdat de loader klaar is / je weer kunt klikken op de knoppen  

### Overzetten van de .csv naar de database
1 - Open de applicatie nadat je de "raw" folder in "src/main/resources/database" hebt geplaatst.  
2 - Druk op de "home" pagina op de knop "Ruwe data converteren naar .csv".  
3 - Druk op de "Beginnen met Parsen" knop en wacht totdat de loader klaar is / je weer kunt klikken op de knoppen  

### Opzetten van de local database
1 - Download jdbc (platform independent) van hier: https://dev.mysql.com/downloads/connector/j/  
2- Voeg toe aan het project(open module settings, libraries en dan de mysql connector toevoegen).  
3 - Heb zelf via localhost(phpmyadmin) sql database opgezet. Sql scriptjes zitten in word bestand. 
Die erin gooien en klaar. Het zijn nu nog 6 aparte bestandjes die het in de database gooien. 
Begin met person en titlebasics anders gaat hij moeilijk doen om de foreign keys. 
Heb foreign keys bij de andere bestanden voor het uploaden uitgezet en erna weer aangezet.