public class Loja extends Imovel
{
    private int area;
    private boolean temWC;
    private String tipoNegocio; // tipo de negócio viável na loja
    private int numDaPorta;
    
    public Loja(){
        super();
        area = 0;
        temWC = false;
        tipoNegocio = "n/a";
        numDaPorta = 0;
    }
    
    public Loja(String id, String rua, double precoPedido, double precoMinimo,
                 int area, boolean temWC, String tipoNegocio, int numDaPorta){
        
        super(id, rua, precoPedido, precoMinimo);
        this.area = area;
        this.temWC = temWC;
        this.tipoNegocio = tipoNegocio;
        this.numDaPorta = numDaPorta;
    }
    
    public Loja(Loja loja){
        super(loja);    
        area = loja.getArea();
        temWC = loja.getTemWC();
        tipoNegocio = loja.getTipoNegocio();
        numDaPorta = loja.getNumDaPorta();
    }
    
    public int getArea(){
        return area;
    }
    
    public boolean getTemWC(){
        return temWC;
    }
    
    public String getTipoNegocio(){
        return tipoNegocio;
    }
    
    public int getNumDaPorta(){
        return numDaPorta;
    }
    
    public void setArea(int area){
        this.area = area;
    }
    
    public void setTemWC(boolean temWC){
        this.temWC = temWC;
    }
    
    public void setTipoNegocio(String tipoNegocio){
        this.tipoNegocio = tipoNegocio;
    }
    
    public void setNumDaPorta(int numDaPorta){
        this.numDaPorta = numDaPorta;
    }
    
    public Loja clone(){
        return new Loja(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Loja l = (Loja) o;
            
            return super.equals(l) && area == l.getArea() && temWC == l.getTemWC() &&
                   tipoNegocio.equals(l.getTipoNegocio()) && numDaPorta == l.getNumDaPorta();
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("-> Loja\n");
        
        sb.append(super.toString());
        sb.append("Área: " + area + "m^2\n");
        sb.append("Tem WC: " + (temWC ? "sim\n" : "não\n"));
        sb.append("Tipo de negócio: " + tipoNegocio + "\n");
        sb.append("Número da porta: " + numDaPorta + "\n");
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + area;
        hash = 31*hash + (temWC ? 1 : 0);
        hash = 31*hash + ((tipoNegocio == null) ? 0 : tipoNegocio.hashCode());
        hash = 31*hash + numDaPorta;
        return hash;
    }   
}
