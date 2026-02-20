package modelo;

import java.util.ArrayList;

public class ColeccionIdentificadores {

    ArrayList<Identificable> listaIdentificables;
    public ColeccionIdentificadores() {
        listaIdentificables = new ArrayList<>();
    }

    public void addIdentificable(Identificable objeto) {
        int id = objeto.getId();
        boolean existe = false;
        for (Identificable identificable : listaIdentificables) {
            if (identificable.getId() == id) {
                objeto.setId();
                existe = true;
                break;
            }
        }
        if (existe) {
            addIdentificable(objeto);
        } else {
            listaIdentificables.add(objeto);
        }
    }

    public Identificable getIdentificable(int id) {
        for (Identificable identificable : listaIdentificables) {
            if (identificable.getId() == id) {
                return identificable;
            }
        }
        throw new IllegalArgumentException("No se ha encontrado el identificable");
    }

    public Identificable getUltimoIdentificable() {
        int length = listaIdentificables.size();
        if (length == 0) {
            return null;
        } else  {
            return listaIdentificables.get(length - 1);
        }
    }

    public void removeIdentificable(Identificable objeto) {
        int id = objeto.getId();
        for (Identificable identificable : listaIdentificables) {
            if (identificable.getId() == id) {
                listaIdentificables.remove(identificable);
                return;
            }
        }
        throw new IllegalArgumentException("No se ha encontrado el identificable");
    }

    public ArrayList<Identificable> getlistaIdentificable() {
        return listaIdentificables;
    }
}
