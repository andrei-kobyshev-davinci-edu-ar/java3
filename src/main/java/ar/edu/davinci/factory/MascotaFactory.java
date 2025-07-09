package ar.edu.davinci.factory;

import ar.edu.davinci.excepciones.TipoMascotaInvalidoException;
import ar.edu.davinci.modelo.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Factory para crear mascotas según su tipo.
 * Aplica el patrón Factory Method y elimina switches (Tell Don't Ask).
 */
public class MascotaFactory {
    private static final Map<String, BiFunction<String, Double, Mascota>> CREADORES_MASCOTA;
    
    static {
        CREADORES_MASCOTA = new HashMap<>();
        CREADORES_MASCOTA.put("perro", Perro::new);
        CREADORES_MASCOTA.put("gato", Gato::new);
        CREADORES_MASCOTA.put("conejo", Conejo::new);
        CREADORES_MASCOTA.put("pajaro", Pajaro::new);
        CREADORES_MASCOTA.put("pájaro", Pajaro::new);
    }
    
    /**
     * Crea una mascota según su tipo.
     * @param tipo Tipo de mascota
     * @param nombre Nombre de la mascota
     * @param peso Peso de la mascota
     * @return La mascota creada
     * @throws TipoMascotaInvalidoException Si el tipo no es válido
     */
    public static Mascota crearMascota(String tipo, String nombre, double peso) 
            throws TipoMascotaInvalidoException {
        
        String tipoNormalizado = tipo.toLowerCase().trim();
        BiFunction<String, Double, Mascota> creador = CREADORES_MASCOTA.get(tipoNormalizado);
        
        if (creador == null) {
            throw new TipoMascotaInvalidoException(tipo);
        }
        
        return creador.apply(nombre, peso);
    }
    
    /**
     * Obtiene los tipos de mascota disponibles.
     * @return Array con los tipos disponibles
     */
    public static String[] getTiposDisponibles() {
        return new String[]{"Perro", "Gato", "Conejo", "Pajaro"};
    }
}