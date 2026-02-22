package beans;

import java.util.HashMap;
import java.util.Map;

public class BeanGenerico {
    private Map<String, Object> colunas = new HashMap<>();

    public void setAtribute(String nomeColuna, Object valor) {
        colunas.put(nomeColuna, valor);
    }

    public Object getAtribute(String nomeColuna) {
        return colunas.get(nomeColuna);
    }
}