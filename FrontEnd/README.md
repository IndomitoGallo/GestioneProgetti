PRE-CONDIZIONE: avere installato nel proprio pc Python 2.6 o 2.7

1 - Scaricare il server NodeJS al seguente link: https://nodejs.org/en/download/.
2 - Innstallare il server al cui interno è già installato il pacchetto npm.
3 - Aprire il server NodeJS (non tramite l'interfaccia principale NodeJS, ma tramite il "Node.js command prompt") e spostarsi nella cartella del FrontEnd scaricato in locale con il comando: cd "directoryPath".
4 - Attivare il comando "npm install" per scaricare tutti i package necessari al funzionamento di Angular2.
5 - Attivare il comando "npm start" per compilare i file e runnare il server.

Potete suddividere il FrontEnd in 4 parti:
- La prima è costituita dal file "index.html", ovvero la Single-Page dell'applicazione e dai file .json presenti nella stessa directory che costituiscono la configurazione del framework.
- La seconda è costituita dalle cartelle "node_modules" e "typings", in cui il server NodeJS ha installato i package del framework.
- La terza è costituita dalla cartella "css", in cui vengono definiti gli stili.
- La quarta è costituita dalla cartella "app", al cui interno è presente tutta la logica del FrontEnd.

NB: Il funzionamento della logica è spiegato più in dettaglio nel file "ReadMe" all'interno della cartella "app".
