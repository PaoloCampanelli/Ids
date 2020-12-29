package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;

import java.util.Date;

public class StatoPacco {
    public StatoPaccoEnum stato;
    public Date dataStatoPacco;

    public StatoPacco(StatoPaccoEnum stato, Date dataStatoPacco)
    {
        this.stato = stato;
        this.dataStatoPacco = dataStatoPacco;
    }
}
