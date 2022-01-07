package com.osservato.osservatore;

public interface Osservato {
    void registraOsservatore(OsservatoreImpl observer);

    void rimuoviOsservatore(OsservatoreImpl observer);

    void nuovoEvento(Evento event);
}
