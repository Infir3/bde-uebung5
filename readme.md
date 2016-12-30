# Überblick

Die Klasse *de.sb.HBase.HBaseTool* löscht die Tabelle *customer* (wenn vorhanden), erstellt diese dann neu und befüllt sie mit Daten.

Die jar wird mit dem Befehl ``java -jar bde-uebung5-0.0.1-SNAPSHOT.jar`` gestartet. Die Datei verfügt über ein eigenes Manifest, deshalb ist der Parameter `-cp` und die Angabe der Main Class nicht mehr nötig.

Die Tabelle kann in der HBase Shell über `disable 'customer'` und `drop 'customer'` wieder gelöscht werden; `scan 'customer'` zeigt den Inhalt der Tabelle an.

Das Projekt wird (wie immer) mit `mvn install` gebaut.

# Probleme und Lösungen

+ ClassNotFoundException: Wegen Fehlern in der POM und nicht Versionskonflikten zwischen Hadoop und HBase (ich habe z.B. für hbase-client Version 1.2.4 verwendet, in der VM ist aber Version 1.1.2.2 installiert) gab es beim Ausführen der jar immer wieder eine ClassNotFoundException.  
  Lösung: Verwendung der richtigen Version von hbase-client (1.1.2) sowie des Maven Shade Plugins, um alle benötigten jars in eine "Über-jar" zu packen.

+ Zookeeper: Wenn die HBaseConfiguration nicht richtig eingestellt ist, kommt es zu folgendem Fehler:  
```Caused by: org.apache.hadoop.hbase.MasterNotRunningException: The node /hbase is not in ZooKeeper. It should have been written by the master. Check the value configured in 'zookeeper.znode.parent'. There could be a mismatch with the one configured in the master.```  
  Lösung: Konfiguration korrekt einstellen, siehe https://community.hortonworks.com/articles/2038/how-to-connect-to-hbase-11-using-java-apis.html
