import java.util.Map;
import java.util.Set;
import java.util.List;

public class Imoobiliaria
{   
    Map<String, Utilizador> utilizadores; // Map que a cada email faz corresponder o respetivo Utilizador
    Map<String, Imovel> imoveis; // Map que a cada id (valido) de imóvel faz corresponder o respetivo objeto da classe Imovel
    Utilizador utilizadorAutenticado = null;
    
    public static void initApp(){
    }
    
    /**
     * Regista um utilizador, quer vendedor, quer comprador.
     * @param utilizador Utilizador a registar
     * @throws UtilizadorExistenteException se a Imoobiliaria já tiver um utilizador que tem o mesmo e-mail que aquele que foi passado para o método.
     */
    public void registarUtilizador(Utilizador utilizador) throws UtilizadorExistenteException{
        String email = utilizador.getEmail();
        
        if(utilizadores.containsKey(email))
            throw new UtilizadorExistenteException("Já existe um utilizador com o e-mail: " + email);
        else
            utilizadores.put(email, utilizador);
    }
    
    /**
     * Valida o acesso à aplicação, utilizando as credenciais (email e password).
     * @param email Email do utilizador.
     * @param password Password do utilizador.
     * @throws SemAutorizacaoException Se o e-mail e/ou a password for(em) inválido(s).
     */
    public void iniciaSessao(String email, String password) throws SemAutorizacaoException{
        Utilizador utilizador = utilizadores.get(email);
        
        if(utilizador == null || !utilizador.validaPassword(password)) // e-mail e/ou a password inválidos
            throw new SemAutorizacaoException("E-mail e/ou palavra-passe inválido(s)");
        else // autenticação bem sucedida
            utilizadorAutenticado = utilizador;
    }
    
    /** Se existir um utilizador autenticado, termina a sessão do mesmo, se não, não faz nada. */
    public void fechaSessao(){
        utilizadorAutenticado = null;
    }
    
    public void registaImovel(Imovel im) throws SemAutorizacaoException, ImovelExisteException{
        if(!(utilizadorAutenticado instanceof Vendedor))
            throw new SemAutorizacaoException("Apenas vendedores têm autorização para registar imóveis.");
        else{
            String idImovel = im.getId();
            Vendedor vendedor = (Vendedor) utilizadorAutenticado;
            
            if(imoveis.containsKey(idImovel))
                throw new ImovelExisteException("Já existe um imóvel com o id: " + idImovel);
            else{
                imoveis.put(idImovel, im);
                vendedor.poeAVenda(idImovel);                
            }
        }
    }
    
    public List<Consulta> getConsultas() throws SemAutorizacaoException{
        // Esta verificação também é feita em registaImovel(). Pensar em fazer um método privado que realize esta verificação!!!
        List<Consulta> ultimasConsultas = new ArrayList<Consulta>();
        Set<String> emVenda;
        
        if(!(utilizadorAutenticado instanceof Vendedor))
            throw new SemAutorizacaoException("Apenas vendedores têm autorização para registar imóveis.");
        else{ // o utilizador autenticado é um vendedor
            Vendedor vendedor = (Vendedor) utilizadorAutenticado;
            emVenda = vendedor.getEmVenda();
            for(String idImovel : emVenda){
                ultimasConsultas.addAll(imoveis.getKey(idImovel).getConsultas());
                Collections.sort(ultimasConsultas);
                
            }
            
        }
        return null; // MUDAR!
    }
    
    public void setEstado(String idImovel, String estado) 
           throws ImovelInexistenteException, SemAutorizacaoException, EstadoInvalidoException
    {
        
    }
        
        
}
