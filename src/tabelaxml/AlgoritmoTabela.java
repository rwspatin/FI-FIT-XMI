/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaxml;

import java.util.ArrayList;

/**
 *
 * @author rwspa
 */

public class AlgoritmoTabela {
    private ArrayList<Classe> dados = new ArrayList<>();
    int contador=0,tamanho=0;
    //lista de id's para o FI
    ArrayList<ID> listID = new ArrayList<>();
    ID id;
    
    ArrayList<String> ordem = new ArrayList<>();
    
    
    public AlgoritmoTabela() {
    }

   
    public void imprime(){
        int tamanho=dados.size();
        int q=0;
        
        
        criaFI();
        listaFIT();
        
        System.out.println("\t\t\t\t-------------");
        System.out.println("\t\t\t\t|   | FI|FIT|");
        
        for(int i=0; i<dados.size();i++){
            System.out.println("\t\t\t\t-------------");
            System.out.println("\t\t\t\t| "+dados.get(i).getNome() + " | "+ dados.get(i).getFi()+" | "+dados.get(i).getFit()+" |");
            
        }
        
        System.out.println("\t\t\t\t-------------");
        System.out.println("\n\n");
        
        ordemTest(tamanho);
        
        
        System.out.println("\t\t\t\t-------------");
        System.out.println("\t\t\t\t|   Ordem   |");
        for(String o:ordem){
            q++;
            System.out.println("\t\t\t\t| "+q+" |\t"+o+"   |");
        }
    }
    
    public void ordemTest(int tamanho){
        
        int menor;
        
        String idRemovido=null;
        
        if(confereValor(0)){
            menor=0;
            for(Classe a:dados){
                if(a.getFit()<=menor){
                    contador++;
                    ordem.add(a.getNome());
                    System.out.println(contador+"ª "+a.getNome());
                    idRemovido=a.getId();
                    dados.remove(a);
                    remontaDados(dados);
                    removeLigacao(idRemovido);
                    listaFIT();
                    break;
                }
            }
        }else if(confereValor(1)){
            menor=1;
            for(Classe a:dados){
                if(a.getFit()<=menor){
                    contador++;
                    System.out.println(contador+"ª "+a.getNome());
                    idRemovido=a.getId();
                    dados.remove(a);
                    remontaDados(dados);
                    removeLigacao(idRemovido);
                    listaFIT();
                    break;
                }
            }
        }else{
            System.out.println("Existe erro -> Não foi encontrado 0 nem 1 no FIT");
            listID = null;
        }
        
        if(contador<tamanho){
            ordemTest(tamanho);
        }
        
       
    }
    
    public void remontaDados(ArrayList<Classe> array){
        ArrayList<Classe> d = new ArrayList<>();
        
        //cria clone sem o null
        for(Classe a:array){
            if(!a.equals(null)){
                d.add(a);
            }
        }
        
        //elimina todos os dados do array
        dados=null;
        dados=new ArrayList<>();
        //seta os dados novamente
        for(Classe b:d){
            dados.add(b);
        }
    }
    
    public void removeLigacao(String idRemovido){
        for(int i=0; i<dados.size();i++){
            if(dados.get(i).getLigacao().size()>0){
                for(int j=0; j<dados.get(i).getLigacao().size();j++){
                    if(idRemovido.equals(dados.get(i).getLigacao().get(j).getDestino())){
                        dados.get(i).getLigacao().remove(j);
                        ArrayList<Ligacao> ligacao = remontaLigacao(dados.get(i).getLigacao());
                        dados.get(i).setLigacao(null);
                        dados.get(i).setLigacao(ligacao);
                    }
                }
            }
        }
    }
    
    public ArrayList<Ligacao> remontaLigacao(ArrayList<Ligacao> a){
        ArrayList<Ligacao> ligacao = new ArrayList<>();
        
        for(Ligacao l:a){
            if(!l.equals(null)){
                ligacao.add(l);
            }
        }
        
        return ligacao;
    }
    
    public boolean confereValor(int i){
        boolean resultado=false;
        int valor=i;
        
        for(Classe a:dados){
            if(valor==a.getFit()){
                resultado=true;
                break;
            }
        }
        return resultado;
    }
    
    public void listaFIT(){
        zeraFIT();
        String dp="Dependency";
        String d="Generalization";
        String a="Association";
        String c="composite";
        String s= "shared";
        
        System.out.println("\n-------------------------------------");
        System.out.println("\tLIGAÇÕES - LÓGICA FIT");
        
        
        for(int i=0; i<dados.size();i++){
            //precorre novamente as classes para ir vendo as ligações
            for(int j=0; j<dados.size();j++){
                //Confere quando for igual
                if(dados.get(i).getId().equals(dados.get(j).getId())){
                    //se não houver ligações printa mensagem somente
                    if(dados.get(j).getLigacao().isEmpty()){
                        System.out.println("\nA Classe "+dados.get(i).getNome()+" não tem ligações na própria tag\n");
                    }else{
                        for(int k=0; k<dados.get(j).getLigacao().size();k++){
                            String nome=null;
                            //Dependencia
                            if(dp.equals(dados.get(j).getLigacao().get(k).getTipo())){
                                setFIT(dados.get(i).getId(), dados.get(j).getLigacao().get(k).getDestino());
                                
                                nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                System.out.println("Classe "+dados.get(i).getNome()+" depende da classe "+nome);
                                
                            //Herança
                            }else if(d.equals(dados.get(j).getLigacao().get(k).getTipo())){
                                setFIT(dados.get(i).getId(), dados.get(j).getLigacao().get(k).getDestino());
                                
                                nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                System.out.println("Classe "+dados.get(i).getNome()+" depende da classe "+nome);
                                
                            //Composição
                            }else if(c.equals(dados.get(j).getLigacao().get(k).getTipo())){
                                setFIT(dados.get(j).getLigacao().get(k).getDestino(),dados.get(i).getId());
                                
                                nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                System.out.println("Classe "+nome+" depende da classe "+dados.get(i).getNome());
                                
                            //Agregação
                            }else if(s.equals(dados.get(j).getLigacao().get(k).getTipo())){
                                setFIT(dados.get(i).getId(), dados.get(j).getLigacao().get(k).getDestino());
                                
                                nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                System.out.println("Classe "+dados.get(i).getNome()+" depende da classe "+nome);
                                
                            //Associação -> multiplicidade
                            }else if(a.equals(dados.get(j).getLigacao().get(k).getTipo())){
                                
                                int l=0,u=0;
                           
                                Complemento destino=new Complemento();
                                //System.out.println(dados.get(j).getLigacao().get(k).getDest());
                                l=dados.get(j).getLigacao().get(k).getDest().getLowerValor();
                                destino.setLowerValor(l);
                                u=dados.get(j).getLigacao().get(k).getDest().getUpperValor();
                                destino.setUpperValor(u);
                            
                                l=0;
                                u=0;
                            
                                Complemento origem=new Complemento();
                                l=dados.get(j).getLigacao().get(k).getOri().getLowerValor();
                                origem.setLowerValor(l);
                                u=dados.get(j).getLigacao().get(k).getOri().getUpperValor();
                                origem.setUpperValor(u);
                            
                                String resultado;
                                resultado=comparaMultiplicidade(origem, destino);
                                
                                switch(resultado){
                                    //se for origem a outra classe que depende de voce, então não deve contar
                                    case "origem":
                                        setFIT(dados.get(j).getLigacao().get(k).getDestino(),dados.get(i).getId());
                                        
                                        nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                        System.out.println("Classe "+dados.get(i).getNome()+" depende da classe "+nome);
                                        
                                        break;
                                    case "destino":
                                        setFIT(dados.get(i).getId(), dados.get(j).getLigacao().get(k).getDestino());
                                        
                                        nome=verificaNome(dados.get(j).getLigacao().get(k).getDestino());
                                        System.out.println("Classe "+dados.get(i).getNome()+" depende da classe "+nome);
                                        
                                        break;
                                    default:
                                        System.out.println("Erro ocorrido com relação a ligação: "+dados.get(i).getNome()+" Associação");
                                        break;
                                }
                                
                            //Se não -> reporta erro
                            }else{
                                System.out.println("Erro ocorrido com relação a ligação: "+dados.get(i).getNome()+"->"+dados.get(j).getLigacao().get(k).getTipo());
                            }
                        }
                    }
                }
            }
        }
        System.out.println("-------------------------------------\n");
    }
    
    public void zeraFIT(){
        for(Classe a :dados){
            a.setFit(0);
        }
    }
    
    
    public String verificaNome(String id){
        String resultado=null;
        for(Classe a:dados){
            if(id.equals(a.getId())){
                resultado=a.getNome();
            }
        }
        return resultado;
    }
    
    public void setFIT(String dependente, String superior){
        
        for(ID a:listID){
            if(superior.equals(a.getId())){
                for(int i=0; i<dados.size();i++){
                    if(dependente.equals(dados.get(i).getId())){
                        dados.get(i).somaFIT(a.getQtd());
                    }
                }
            }
        }
    }
    
    
    public void criaFI(){
        preencheQtdEmID();
        for(int i=0; i<dados.size();i++){
            for(int j=0;j<listID.size();j++){
                if(dados.get(i).getId().equals(listID.get(j).getId())){
                    dados.get(i).setFi(listID.get(j).getQtd());
                }
            }
        }
        
    }
    
    public void preencheQtdEmID(){
        
        preencheID();
        
        
        String dp="Dependency";         //Dependencia
        String d="Generalization";      //Herança
        String a="Association";         //Associação com multiplicidade
        String c="composite";           //Composição
        String s= "shared";             //Agregação
        
        
        System.out.println("\n-------------------------------------");
        System.out.println("\tLIGAÇÕES - LÓGICA FI");
        //le todas as classes
        for(int i=0;i<dados.size();i++){
            
            //percorre todas as classes novamente para ir conferindo
            for(int j=0; j<dados.size();j++){        
                
                //a classe não pode ser dependente dela mesma
                if(!(dados.get(j).getNome().equals(dados.get(i).getNome()))){
                    
                    //percorre as ligacoes dessa classe
                    for(int k=0; k<dados.get(j).getLigacao().size();k++){
                        
                        //se for igual generalizacao -> FUNCIONA
                        if(d.equals(dados.get(j).getLigacao().get(k).getTipo())){
                            
                            //se essa ligacao for com dependencia para a classe que esta sendo conferida conta um
                            if(dados.get(j).getLigacao().get(k).getDestino().equals(dados.get(i).getId())){
                                setaQTDID(dados.get(i).getId());
                                System.out.println(dados.get(i).getNome()+" <- "+dados.get(j).getNome()+"\t|herança");
                            }
                            
                        //se for composicao -> FUNCIONA
                        }else if(c.equals(dados.get(j).getLigacao().get(k).getTipo())){
                            //na composicao a logica é inversa, o que vai liberar é a classe origem
                            if(dados.get(j).getLigacao().get(k).getDestino().equals(dados.get(i).getId())){
                                setaQTDID(dados.get(j).getId());
                                System.out.println(dados.get(i).getNome()+" -> "+dados.get(j).getNome()+"\t|composição");
                            }
                            
                        //se for share -> FUNCIONA
                        }else if(s.equals(dados.get(j).getLigacao().get(k).getTipo())){
                            
                            //na composicao a logica é inversa, o que vai liberar é a classe origem
                            if(dados.get(j).getLigacao().get(k).getDestino().equals(dados.get(i).getId())){
                                setaQTDID(dados.get(i).getId());
                                System.out.println(dados.get(i).getNome()+" <- "+dados.get(j).getNome()+"\t|agregação");
                            }
                            
                        //dependencia -> FUNCIONA
                        }else if(dp.equals(dados.get(j).getLigacao().get(k).getTipo())){
                            if(dados.get(j).getLigacao().get(k).getDestino().equals(dados.get(i).getId())){
                                setaQTDID(dados.get(i).getId());
                                System.out.println(dados.get(i).getNome()+" <- "+dados.get(j).getNome()+"\t|dependencia");
                            }
                            
                        //se for Associação confere as multiplicidades
                        }else if(a.equals(dados.get(j).getLigacao().get(k).getTipo())){ //TEM QUE TRATAR OS VALORES
                            if(dados.get(j).getLigacao().get(k).getDestino().equals(dados.get(i).getId())){
                                int l=0,u=0;
                           
                                //calcula multiplicidade de destino
                                Complemento destino=new Complemento();
                                //System.out.println(dados.get(j).getLigacao().get(k).getDest());
                                l=dados.get(j).getLigacao().get(k).getDest().getLowerValor();
                                destino.setLowerValor(l);
                                u=dados.get(j).getLigacao().get(k).getDest().getUpperValor();
                                destino.setUpperValor(u);
                            
                                l=0;
                                u=0;
                            
                                //calcula multiplicidade de origem
                                Complemento origem=new Complemento();
                                l=dados.get(j).getLigacao().get(k).getOri().getLowerValor();
                                origem.setLowerValor(l);
                                u=dados.get(j).getLigacao().get(k).getOri().getUpperValor();
                                origem.setUpperValor(u);
                            
                                //chama função que confere multiplicidade
                                String resultado;
                                resultado=comparaMultiplicidade(origem, destino);
                                
                                
                                switch(resultado){
                                    case "origem":
                                        setaQTDID(dados.get(j).getId());
                                        System.out.println(dados.get(i).getNome()+" -> "+dados.get(j).getNome()+"\t|Associação ->"+resultado);
                                        break;
                                    case "destino":
                                        setaQTDID(dados.get(i).getId());
                                        System.out.println(dados.get(i).getNome()+" <- "+dados.get(j).getNome()+"\t|Associação ->"+resultado);
                                        break;
                                    default:
                                        System.out.println("Erro ocorrido com relação a ligação: "+dados.get(i).getNome()+":"+dados.get(j).getNome());
                                        break;
                                }
                            }
                            
                          //se não for nenhuma das determinadas reporta erro
                        }else{
                            System.out.println("DEU ERRO PARA RECONHECER LIGACAO "+dados.get(j).getNome()+" "+dados.get(j).getLigacao().get(k).getTipo());
                        }
                        
                    }
                }
            }
        }
        System.out.println("-------------------------------------\n");
        
    }
    
    
    //percorre a lista de id e quando achar o id passado soma um para esse ID
    public void setaQTDID(String id){
        
        for(int i=0; i<listID.size();i++){
            //quando encontrar o id correspondente soma 1 no FI temporário da lista
            if(id.equals(listID.get(i).getId())){
                listID.get(i).adicionaUmEmQtd();
                //System.out.println("adicionado um em "+listID.get(i));
            }
        }
    }
    
    //percorre toda a lista de classes e seta os id's na lista de id
    public void preencheID(){
        
        for(Classe a:dados){
            id = new ID(a.getId());
            listID.add(id);
        }
    }
    
    //Retorna quem é o principal
    public String comparaMultiplicidade(Complemento ori, Complemento dest){
        
        int origem = this.calculaMutiplicidade(ori);
        int destino = this.calculaMutiplicidade(dest);
        
        //Origem p destino
        
        //1 p 1*
        if((origem<destino)&&(origem!=0)){
            return "origem";
        //1 p 0
        }else if(origem==1&& destino==0){
            return "origem";
        //1 p 1 -> escolhe destino
        }else if(origem==1 && destino==1){
            return "destino";
        //1* p 1
        }else if((destino<origem)&&(destino!=0)){
            return "destino";
        //0 p 1
        }else if(destino==1 && origem==0){
            return "destino";
        }else{
            return "";
        }
    }
    
    //multiplica o valor lower e upper para ter um só número
    //OBS: quando é * na leitura do xml ele substitui por 42
    public int calculaMutiplicidade(Complemento c){
        
        int resultado=0,a=0,b=0;
        
        a=c.getLowerValor();
        //System.out.println(a);
        
        b=c.getUpperValor();
        //System.out.println(b);
        
        resultado=a*b;
        return resultado;
    }
    
    
    /**
     * @return the table
     */
    public ArrayList<Classe> getTable() {
        return dados;
    }

    /**
     * @param table the table to set
     */
    public void setTable(ArrayList<Classe> table) {
        this.dados = table;
    }
    
    
}
