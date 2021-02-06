package it.unicam.cs.ids.c3spa.test;

import it.unicam.cs.ids.c3spa.core.Servizi;
import it.unicam.cs.ids.c3spa.core.StatoPacco;
import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class StatoPaccoTest {

    @Test
    public void costruttoreStatoPacco(){
        StatoPacco primoStato = new StatoPacco(StatoPaccoEnum.preparato, Servizi.dataUtilToSql(java.util.Date.from(Instant.now())));
        StatoPacco secondoStato = new StatoPacco(StatoPaccoEnum.assegnato, Date.valueOf("2021-01-24"));
        StatoPacco terzoStato = new StatoPacco();
        assertNotEquals(primoStato, secondoStato);
        assertNotEquals(primoStato, terzoStato);
    }

    @Test
    public void StatoPacco(){
        StatoPacco primoStato = new StatoPacco(1, StatoPaccoEnum.preparato, Servizi.dataUtilToSql(java.util.Date.from(Instant.now())));
        StatoPacco secondoStato = new StatoPacco(2, StatoPaccoEnum.assegnato, Date.valueOf("2021-01-24"));
        StatoPacco terzoStato = new StatoPacco();
        assertNotEquals(primoStato, secondoStato);
        assertNotEquals(primoStato, terzoStato);
    }

}