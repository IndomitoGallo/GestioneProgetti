1 - Il file "main.ts" è il punto di partenza del FrontEnd, in quanto vengono importate le librerie che serviranno dal framework e soprattutto viene settato quello che sarà il punto di partenza della logica, ovvero il modulo AppComponent.

2 - Il file "app.component.ts" contiene al suo interno un Componente che definisce e amministra tutti i Path dell'applicazione.

3 - All'interno delle cartelle (a parte "model", dove ci sono gli oggetti che ho definito nel model di Angular2) potete trovare i vari workflows dell'applicazione che ho diviso logicamente. Nella maggior parte dei casi, ad un Workflow corrisponde un Component appropriato, ma penso che farò un file .service (per i servizi che si collegano al RestControlleri) unico per ognuno dei 4 profili dell'applicazione. 

4 - Ognuno dei 4 profili ha un file Component di base associato. Ad esempio, l'Admin ha un AdminComponent, che poi è in grado di gestire ed amministrare i Component relativi agli altri workflows che potete capire dal modo in cui li ho chiamati.
