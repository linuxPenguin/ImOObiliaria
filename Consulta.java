import java.util.GregorianCalendar;

public class Consulta
{   
    // Variaveis de instancia
    private GregorianCalendar data; // data da consulta
    private String email; // email do utilizador que fez a consulta (valor opcional) 
    
    /**
     * Construtor por omissao
     */
    public Consulta(){
        data = new GregorianCalendar(); // data atual
        email = null; // e-mail desconhecido
    }
    
    /**
     * Construtor parametrizado
     */
    public Consulta(String email){
        this.email = email;
        this.data = new GregorianCalendar();
    }
      
    /**
     * Construtor de copia
     */
    public Consulta(Consulta c){
        this.email = c.email;
        this.data = (data == null) ? null : (GregorianCalendar) data.clone();
    }
    
    // Getters
    public GregorianCalendar getData(){
        return (data == null)? null : (GregorianCalendar) data.clone();
    }
    
    public String getEmail(){
        return email;
    }

    /* Este tipo é Imutável, depois de feita uma consulta, esta não pode ser alterada */
    
    public boolean feitaPorUtilizadorRegistado(){
        return email != null;
    }
    
    public Consulta clone(){
        return new Consulta(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Consulta c = (Consulta) o;
        return data.equals(c.getData()) && email.equals(c.getEmail());
    }
    
    public String toString(){
        String aux = (data == null) ? "n/a" : data.toString(); 
        return "E-mail: " + email + "\nData: " + aux + "\n";
    }
    
    public int hashCode(){
        int hash, hashEmail, hashData;
        hash = 7;
        hashEmail = email == null ? 0 : email.hashCode();
        hashData = data == null ? 0 : data.hashCode();
        
        hash = 31 * hash + hashEmail;
        hash = 31 * hash + hashData;
        return hash;
    }
}
