package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.core.astratto.StatoPaccoEnum;

import java.util.Date;

public class StatoPacco {
    public int id;
    public StatoPaccoEnum stato;
    public Date dataStatoPacco;

    public StatoPacco(int id, StatoPaccoEnum stato, Date dataStatoPacco)
    {
        this.id=id;
        this.stato = stato;
        this.dataStatoPacco = dataStatoPacco;
    }

    public StatoPacco(StatoPaccoEnum stato, Date dataStatoPacco)
    {
        this.stato = stato;
        this.dataStatoPacco = dataStatoPacco;
    }

    @Override
    public String toString() {
        return "StatoPacco{" +
                "id=" + id +
                ", stato=" + stato +
                ", dataStatoPacco=" + dataStatoPacco +
                '}';
    }
}
