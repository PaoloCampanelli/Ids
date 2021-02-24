package it.unicam.cs.ids.c3spa.core;

import it.unicam.cs.ids.c3spa.astratto.IMapData;
import it.unicam.cs.ids.c3spa.astratto.StatoPaccoEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StatoPacco implements IMapData<StatoPacco> {
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

    public StatoPacco() {
    }

    @Override
    public String toString() {
        return "StatoPacco{" +
                "id=" + id +
                ", stato=" + stato +
                ", dataStatoPacco=" + dataStatoPacco +
                '}';
    }

    @Override
    public StatoPacco mapData(ResultSet rs) throws SQLException {

        this.id = rs.getInt("statoId");

        String statoS= rs.getString("stato").toUpperCase();
        switch (statoS) {
            case ("PREPARATO"):
                this.stato = StatoPaccoEnum.preparato;
                break;
            case ("ASSEGNATO"):
                this.stato = StatoPaccoEnum.assegnato;
                break;
            case ("CONSEGNATO"):
                this.stato = StatoPaccoEnum.consegnato;
                break;
            default:
                this.stato=StatoPaccoEnum.preparato;
        }

        dataStatoPacco=rs.getDate("data");

        return this;
    }
}
