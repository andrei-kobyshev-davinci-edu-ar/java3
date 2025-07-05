package ar.edu.davinci.modelo;

/**
 * Representa una mascota.
 */
public class Mascota {
    private Long id;
    private String nombre;
    private String tipo;
    private double peso;
    
    public Mascota() {}
    
    public Mascota(String nombre, String tipo, double peso) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public String getRecomendaciones() {
        switch (tipo) {
            case "Perro":
                return "Paseos diarios, vacunas al día, alimentación balanceada";
            case "Gato":
                return "Arenero limpio, rascador, juguetes interactivos";
            case "Conejo":
                return "Heno fresco, verduras variadas, espacio para saltar";
            case "Pajaro":
                return "Jaula espaciosa, baños de sol, semillas frescas";
            default:
                return "Cuidados generales para mascota";
        }
    }
}