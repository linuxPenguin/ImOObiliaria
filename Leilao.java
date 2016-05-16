/**
 * Classe que representa um leilão.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.Iterator;

public class Leilao implements Serializable /* implementar Comparable se for para fazer varios leiloes */
{
    private String responsavel;
    private String imovelEmLeilao;
    private List<Licitador> licitadores; /* vai ter a lista de todos os licitadores por ordem de registo no leilao */
    private int duracao, precoMinimo;
    private long inicioLeilao;
    private boolean leilaoBloqueado;

    private Leilao(){}

    public Leilao(String imovelEmLeilao, String responsavel, int duracao, int precoMinimo){
        this.responsavel = responsavel;
        this.imovelEmLeilao = imovelEmLeilao;
        this.duracao = duracao;
        this.licitadores = new ArrayList<>();
        this.inicioLeilao = System.currentTimeMillis();
        this.precoMinimo = precoMinimo;
        this.leilaoBloqueado = false;
    }

    /** @return id do vendedor responsável pelo leilão. */
    public String getResponsavel(){
        return responsavel;
    }

    /* o id do comprador corresponde ao seu mail*/
    public void registaCompradorLeilao(String idComprador, int limite, int incrementos, int minutos) 
    throws LeilaoTerminadoException
    {
        if(leilaoBloqueado || this.terminouLeilao())
            throw new LeilaoTerminadoException("Não pode adicionar mais vendedores, o leilão terminou.");

        int minutosDesdeInicioLeilao = (int) ((System.currentTimeMillis() - inicioLeilao)/60000);
        Licitador novoLicitador = new Licitador(idComprador, limite, incrementos, minutos, minutosDesdeInicioLeilao);
        licitadores.add(novoLicitador);
    }

    /** @return true se este leilão terminou. */
    public boolean terminouLeilao(){
        long fimDoLeilao = inicioLeilao + duracao * 60 * 60 * 1000;
        return System.currentTimeMillis() >= fimDoLeilao;
    }

    /* simula o leilao e faz log das licitaçoes para um ficheiro "leilao.txt" */
    public String simulaLeilao() throws java.io.IOException{
        int duracaoMinutos = 60 * duracao;
        int precoAtual = 0;
        int proxI;
        Licitador aGanhar = null;
        FileWriter fw = new FileWriter("leilao.txt", false);

        /*copiar estado antes de executar para ser possivel fazer simulaçoes nao-destrutivas dos leiloes*/
        List<Licitador> copiaLicitadores = new ArrayList<>(licitadores);

        fw.write("\n----------- Participantes ----------------\n");
        for(Licitador l : copiaLicitadores){
            fw.write(l.entradaLog());
        }
        fw.write("\n---------------- Imovel ------------------\n");
        fw.write(imovelEmLeilao + " precoMin:" + precoMinimo +"\n"); 
        fw.write("\n----------------- Log --------------------\n");

        for(int i = 0; i <= duracaoMinutos; i = proxI){
            proxI = i + 1;
            Iterator<Licitador> iter = copiaLicitadores.iterator();
            while(iter.hasNext()){  
                Licitador l = iter.next();
                int minutoProxLicitacao = l.getQuandoProximaLicitacao();

                if(minutoProxLicitacao == i){
                    if(l == aGanhar){
                        l.atualizaQuandoProxIncremento();
                    }
                    else if(l.getLimite() <= precoAtual){ 
                        //copiaLicitadores.remove(l);                        
                    }    
                    else{
                        precoAtual = l.setMenorLicitacaoQuePasse(precoAtual);
                        aGanhar = l;
                        l.atualizaQuandoProxIncremento();
                        /*l.atualizaValorProxIncremento();*/
                        fw.write(l.getIdComprador() + " oferta:" + precoAtual + " minuto:" + i + "\n");
                    }
                } else if(minutoProxLicitacao < proxI){
                    proxI = i;
                }
            }
        }

        fw.write("\n----------------- Log --------------------\n");

        /* fazer reset a todos os licitadores */
        for(Licitador l : licitadores)
            l.reset();
        return (aGanhar == null || precoAtual < precoMinimo)? null : aGanhar.getIdComprador();
    }

    public void bloqueiaLeilao(){
        this.leilaoBloqueado = true;
    }
}
