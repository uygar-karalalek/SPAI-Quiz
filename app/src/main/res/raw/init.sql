CREATE TABLE `user` (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,    username VARCHAR (45) UNIQUE                          NOT NULL,    password VARCHAR (45) NOT NULL, points INT DEFAULT (0));
CREATE TABLE category(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR (45) UNIQUE NOT NULL);
CREATE TABLE question ( id INTEGER PRIMARY KEY NOT NULL, value VARCHAR (45) NOT NULL, category INTEGER NOT NULL, wright INTEGER NOT NULL, response1 VARCHAR (45) NOT NULL, response2 VARCHAR (45) NOT NULL, response3 VARCHAR (45) NOT NULL, response4 VARCHAR (45) NOT NULL, additional_data VARCHAR (45), win_points INTEGER DEFAULT (0), FOREIGN KEY(category) REFERENCES category(id));

INSERT INTO category(1, "Film & Serie")
INSERT INTO category(2, "Cultura generale")
INSERT INTO category(3, "Musica")
INSERT INTO category(4, "Programmazione")
INSERT INTO category(5, "Algoritmi")

INSERT INTO question(null, 4, 3, "Qual è il concetto, in programmazione OOP, che permette di comporre le classi in una gerarchia?", "Polimorfismo", "Type-Inference", "Ereditarietà", "Covarianza", null, 1);
INSERT INTO question(null, 4, 3, "Quale funzione, in kotlin, ti permette di creare una mappa mutabile con oggetti stringa?", "mapOf<String>()", "mutableMapOf<String>()", "Map.of<String>()", "CollectionUtils.mapOf<String>()", null, 3);
INSERT INTO question(null, 4, 4, "Qual è il framework di Android che permette il supporto delle sue librerie legacy?", "Spring", "Android Studio", "androidx", "Android Jetpack", null, 4);
INSERT INTO question(null, 4, 1, "Senza di cosa è impossibile creare un'app android (che non sia un widget)?", "Activity", "Fragment", "Navigator", "Kotlin", null, 2);
INSERT INTO question(null, 4, 2, "Qual è il l'RDBMS standard usato per gestire la persistenza in android?", "MySQLite", "SQLite", "LiteMySQL", "MongoLiteDb", null, 1);
INSERT INTO question(null, 4, 1, "Quale software (in primo piano) permette in modo semplice la gestione dei cosiddetti 'container' virtuali e garantisce la compatibilità con quasi tutti i progetti software?", "Docker", "Kotlin", "Java Virtual Machine", "Spring Security", null, 2);
INSERT INTO question(null, 4, 2, "Quale tipo di file, in Java, ti permette di gestire il comportamento di una classe con il vincolo di lasciarti creare solo proprietà statiche e metodi astratti?", "Classi Generiche", "Interfacce", "Classi Astratte", "Data class", null, 3);
INSERT INTO question(null, 4, 2, "Che cosa, nella programmazione, è considerato memory leak?", "Puntare ad un indirizzo di memoria di un altro servizio", "Quando non si dealloca la memoria", "Quando si chiamano dei metodi in loop", "Quando le classi non vengono implementate correttamente", null, 3);
INSERT INTO question(null, 5, 1, "Quale di questi algoritmi è spiegato nel video?", "Insertion sort", "Merge sort", "Selection sort", "Switch sort", "https://www.codesdope.com/staticroot/images/algorithm/sorting3.gif", 3);
INSERT INTO question(null, 5, 2, "Quale di questi algoritmi è spiegato nel video?", "Apple sort", "Min sort", "Bubble sort", "Hexagon sort", "https://upload.wikimedia.org/wikipedia/commons/2/2a/Bubble_sort_with_flag.gif", 3);
INSERT INTO question(null, 5, 2, "Quale di questi algoritmi è spiegato nel video?", "Merge sort", "Linear search", "Key sort", "Binary search", "https://lh3.googleusercontent.com/proxy/n4lEdHRjV2nNJhosOvFEr17C-fre78sIqu7Byzb8wRBdI5SYcS55ADcntHNk6jVOWzmRasz1FSa0kzT4XZp6sQcaYYT4EuMCp6cXPFeRzks", 3);