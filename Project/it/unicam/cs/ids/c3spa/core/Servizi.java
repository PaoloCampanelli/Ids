package it.unicam.cs.ids.c3spa.core;


import it.unicam.cs.ids.c3spa.gestori.GestoreBase;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;


public class Servizi {
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    private SecretKey key;

    public static String caricamento() throws Exception {

        Connection conn = GestoreBase.ApriConnessione();
        Statement stmt= conn.createStatement();
        String s =new Servizi().encrypt("123456");
        stmt.execute("CREATE TABLE IF NOT EXISTS`progetto_ids`.`negozi` (\n" +
                "  `negozioId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `token` INT NULL DEFAULT 5,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`negozioId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`clienti` (\n" +
                "  `clienteId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`clienteId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`categoriemerceologiche` (\n" +
                "  `categoriaId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `nome` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`categoriaId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`negozio_categoriemerceologiche` (\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `categoriaId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`negozioId`, `categoriaId`),\n" +
                "  FOREIGN KEY (negozioId) REFERENCES negozi(negozioId));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pacchi` (\n" +
                "  `paccoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `destinatario` INT NOT NULL,\n" +
                "  `mittente` INT NOT NULL,\n" +
                "  `corriere` INT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `dataPreparazione` DATE NOT NULL,\n" +
                "  `dataConsegnaRichiesta` DATE NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`paccoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pacco_statipacco` (\n" +
                "  `paccoId` INT NOT NULL,\n" +
                "  `statoId` INT NOT NULL,\n" +
                "  PRIMARY KEY (`paccoId`, `statoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`statipacchi` (\n" +
                "  `statoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `stato` ENUM('preparato', 'assegnato', 'consegnato') NOT NULL,\n" +
                "  `data` DATE NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`statoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`sconti` (\n" +
                "  `scontoId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `tipo` VARCHAR(10) NOT NULL,\n" +
                "  `dataInizio` DATE NOT NULL,\n" +
                "  `dataFine` DATE NOT NULL,\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `categoriaMerceologicaId` INT NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`scontoId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`corrieri` (\n" +
                "  `corriereId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`corriereId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`pubblicita` (\n" +
                "  `pubblicitaId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `dataInizio` DATE NOT NULL,\n" +
                "  `dataFine` DATE NOT NULL,\n" +
                "  `negozioId` INT NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`pubblicitaId`));");

        stmt.execute("CREATE TABLE IF NOT EXISTS `progetto_ids`.`amministratori` (\n" +
                "  `amministratoreId` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `denominazione` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.citta` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.numero` VARCHAR(10) NOT NULL,\n" +
                "  `indirizzo.cap` VARCHAR(5) NOT NULL,\n" +
                "  `indirizzo.via` VARCHAR(45) NOT NULL,\n" +
                "  `indirizzo.provincia` VARCHAR(2) NOT NULL,\n" +
                "  `telefono` VARCHAR(20) NOT NULL,\n" +
                "  `eMail` VARCHAR(45) NOT NULL,\n" +
                "  `password` VARCHAR(45) NOT NULL,\n" +
                "  `isCancellato` BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                "  PRIMARY KEY (`amministratoreId`))" );

        stmt.execute("INSERT IGNORE INTO `amministratori`\n" +
                "SET amministratoreId = 1\n" +
                ", denominazione = \"amministratore AN\"\n" +
                ", `indirizzo.citta` = \"citta\"\n" +
                ", `indirizzo.numero` = \"numero\"\n" +
                ", `indirizzo.cap` = \"cap\"\n" +
                ", `indirizzo.via` = \"via\"\n" +
                ", `indirizzo.provincia` = \"AN\"\n" +
                ", telefono = \"1234567890\"\n" +
                ", eMail = \"AMMINISTRATORE.AN@MAIL\"\n" +
                ", password = \"gF8J9KhXOcc=\"\n" +
                ", isCancellato = 0");

        stmt.execute("INSERT IGNORE INTO `amministratori`\n" +
                "SET amministratoreId = 2\n" +
                ", denominazione = \"amministratore AP\"\n" +
                ", `indirizzo.citta` = \"citta\"\n" +
                ", `indirizzo.numero` = \"numero\"\n" +
                ", `indirizzo.cap` = \"cap\"\n" +
                ", `indirizzo.via` = \"via\"\n" +
                ", `indirizzo.provincia` = \"AP\"\n" +
                ", telefono = \"1234567890\"\n" +
                ", eMail = \"AMMINISTRATORE.AP@MAIL\"\n" +
                ", password = \"gF8J9KhXOcc=\"\n" +
                ", isCancellato = 0");

        stmt.execute("INSERT IGNORE INTO `amministratori`\n" +
                "SET amministratoreId = 3\n" +
                ", denominazione = \"amministratore FM\"\n" +
                ", `indirizzo.citta` = \"citta\"\n" +
                ", `indirizzo.numero` = \"numero\"\n" +
                ", `indirizzo.cap` = \"cap\"\n" +
                ", `indirizzo.via` = \"via\"\n" +
                ", `indirizzo.provincia` = \"FM\"\n" +
                ", telefono = \"1234567890\"\n" +
                ", eMail = \"AMMINISTRATORE.FM@MAIL\"\n" +
                ", password = \"gF8J9KhXOcc=\"\n" +
                ", isCancellato = 0");

        stmt.execute("INSERT IGNORE INTO `amministratori`\n" +
                "SET amministratoreId = 4\n" +
                ", denominazione = \"amministratore MC\"\n" +
                ", `indirizzo.citta` = \"citta\"\n" +
                ", `indirizzo.numero` = \"numero\"\n" +
                ", `indirizzo.cap` = \"cap\"\n" +
                ", `indirizzo.via` = \"via\"\n" +
                ", `indirizzo.provincia` = \"MC\"\n" +
                ", telefono = \"1234567890\"\n" +
                ", eMail = \"AMMINISTRATORE.MC@MAIL\"\n" +
                ", password = \"gF8J9KhXOcc=\"\n" +
                ", isCancellato = 0");

        stmt.execute("INSERT IGNORE INTO `amministratori`\n" +
                "SET amministratoreId = 5\n" +
                ", denominazione = \"amministratore PU\"\n" +
                ", `indirizzo.citta` = \"citta\"\n" +
                ", `indirizzo.numero` = \"numero\"\n" +
                ", `indirizzo.cap` = \"cap\"\n" +
                ", `indirizzo.via` = \"via\"\n" +
                ", `indirizzo.provincia` = \"PU\"\n" +
                ", telefono = \"1234567890\"\n" +
                ", eMail = \"AMMINISTRATORE.PU@MAIL\"\n" +
                ", password = \"gF8J9KhXOcc=\"\n" +
                ", isCancellato = 0");



        stmt.close();
        conn.close();
        return "Database caricato";
    }

    public static java.sql.Date dataUtilToSql(java.util.Date data){

        return new java.sql.Date(data.getTime());
    }

    public static java.util.Date dataSqlToUtil(java.sql.Date data){

        return new java.util.Date(data.getTime());
    }

    public void TrippleDes() throws Exception {
        myEncryptionKey = "SecretEcryptionKeySecretEncryptionKey";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }


    public String encrypt(String unencryptedString) throws Exception {
        TrippleDes();
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    public String decrypt(String encryptedString) throws Exception {
        TrippleDes();
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

}
