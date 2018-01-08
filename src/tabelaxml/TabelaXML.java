/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaxml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author rwspa
 */
public class TabelaXML {

    public static void main(String argv[]) {
    ArrayList <Ligacao> li;
    ArrayList<Classe> dados = new ArrayList<>();
    Ligacao ligacao;
    int contador=0;
    Scanner ler = new Scanner(System.in);
    String endereco;
    try {
        //diagramaDeClassesVazio.xml
        System.out.println("Escreve o endereço do xmi: ");
        endereco = ler.next();
	File fXmlFile = new File(endereco);
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(fXmlFile);

	doc.getDocumentElement().normalize();

	//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	NodeList nList = doc.getElementsByTagName("packagedElement");

	System.out.println("----------------------------");

	for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
                Element teste = (Element) nNode;
		//System.out.println("\nCurrent Element :" + nNode.getNodeName());

		if ((nNode.getNodeType() == Node.ELEMENT_NODE)&&(!teste.getAttribute("name").equals("Model"))&&(!teste.getAttribute("name").equals("Modelo"))) {

                    Element eElement = (Element) nNode;
                    
                    //Cria um objeto de classe com o que foi recebido
                    Classe a = new Classe(eElement.getAttribute("xmi:id"),eElement.getAttribute("name"));
                    //Cada classe possui um arraylist de ligacao
                    li = new ArrayList<>();
                    
                    if(eElement.getElementsByTagName("ownedMember").getLength()>0){
                        
                        
                        
                        for(int i=0; i<eElement.getElementsByTagName("ownedMember").getLength();i++){
                            
                            //cada ownedMemeber é uma ligacao
                            ligacao=new Ligacao();
                           
                            
                            NodeList nodesDet = doc.getElementsByTagName("ownedMember");
                            Node n = nodesDet.item(contador);
                            Element e = (Element) n;
                            
                            if(e.getElementsByTagName("ownedEnd").getLength()>0){
                                
                                //cria variaveis para setar nos objetos mais tarde
                                String origem=null, destino=null, tipo=null;
                                Complemento ori; 
                                Complemento dest;
                                
                                //Enquanto existir ligacao executa
                                for(int j=0; j<e.getElementsByTagName("ownedEnd").getLength();j++){
                                    //o tipo é localizado na origem
                                    int l=0, u=0;
                                    NodeList no = doc.getElementsByTagName("ownedEnd");
                                    Node it = nodesDet.item(contador);
                                    Element el = (Element) it;
                                    //se for zero é origem, sempre começa na origem
                                    if(j==0){
                                        ori = new Complemento();
                                        String t = e.getElementsByTagName("ownedEnd").item(j).getAttributes().getNamedItem("aggregation").toString().replaceAll("aggregation=", "").replaceAll("\"", "");
                                        //Se o que estiver na ownedend for nulo o tipo que sera setado sera o da ownedmember
                                        if(!(t.equals("none"))){
                                            tipo=t;
                                        }else{
                                            tipo=e.getAttribute("xmi:type").replaceAll("uml:", "");
                                        }
                                        
                                        ligacao.setTipo(tipo);
                                        
                                        //seta o id da origem
                                        origem=e.getElementsByTagName("ownedEnd").item(j).getAttributes().getNamedItem("type").toString();
                                        ligacao.setOrigem(origem);
                                       // System.out.println("origem id: "+ligacao.getOrigem());
                                        
                                        //se tiver filhos executa
                                        if(e.getElementsByTagName("ownedEnd").item(j).hasChildNodes()){
                                            String low = el.getElementsByTagName("lowerValue").item(j).getAttributes().getNamedItem("value").toString().replaceAll("value=", "").replaceAll("\"", "");
                                            String up = el.getElementsByTagName("upperValue").item(j).getAttributes().getNamedItem("value").toString().replaceAll("value=", "").replaceAll("\"", "");
                                            
                                            if(low.equals("*")){
                                                l=42;
                                            }else{
                                                l=Integer.parseInt(low);
                                            } 
                                            
                                            if(up.equals("*")){
                                                u=42;
                                            }else{
                                                u=Integer.parseInt(up);
                                            }
                                            
                                            ori.setLowerValor(l);
                                            ori.setUpperValor(u);
                                            ligacao.setOri(ori);
                                        }
                                       // System.out.println(ori.getLowerValor()+" "+ori.getUpperValor());
                                    }
                                    if(j==1){
                                        dest = new Complemento();
                                        destino=e.getElementsByTagName("ownedEnd").item(j).getAttributes().getNamedItem("type").toString().replaceAll("type=", "").replaceAll("\"", "");
                                        ligacao.setDestino(destino);
                                        //System.out.println("Destino id: "+ligacao.getDestino());
                                        //se tiver filhos executa
                                        if(e.getElementsByTagName("ownedEnd").item(j).hasChildNodes()){
                                            String low = el.getElementsByTagName("lowerValue").item(j).getAttributes().getNamedItem("value").toString().replaceAll("value=", "").replaceAll("\"", "");
                                            String up = el.getElementsByTagName("upperValue").item(j).getAttributes().getNamedItem("value").toString().replaceAll("value=", "").replaceAll("\"", "");
                                           // System.out.println("low: "+low+" up: "+up);
                                            
                                            if(low.equals("*")){
                                                l=42;
                                            }else{
                                                l=Integer.parseInt(low);
                                            } 
                                            
                                            if(up.equals("*")){
                                                u=42;
                                            }else{
                                                u=Integer.parseInt(up);
                                            }
                                            
                                            dest.setLowerValor(l);
                                            dest.setUpperValor(u);
                                            ligacao.setDest(dest);
                                        }
                                        //System.out.println("Destino");
                                         //System.out.println(dest.getLowerValor()+" "+dest.getUpperValor());
                                    }
                                    
                                
                                
                                }
                            }else if(e.getElementsByTagName("ownedEnd").getLength()==0){
                                //OBS: esse funciona com certeza
                                
                                Element elem = doc.getDocumentElement();
                                NodeList nod = elem.getElementsByTagName("ownedMember");
                                Element el = (Element) nod.item(contador);
                        
                                String destino = e.getAttribute("supplier");
                                ligacao.setDestino(destino);
                                
                                String tipo= e.getAttribute("xmi:type").toString().replaceAll("uml:", "");
                                ligacao.setTipo(tipo);
                            }
                            contador++;
                            //adiciona essa ligacao a lista
                            li.add(ligacao);
                            
                        }
                        //quando preencher as ligacoes acaba
                        a.setLigacao(li);
                    }
                    if(eElement.getElementsByTagName("generalization").getLength()>0){
                        //OBS: Esse funciona com certeza
                        
                        ligacao = new Ligacao();
                        Element elem = doc.getDocumentElement();
                        NodeList nod = elem.getElementsByTagName("generalization");
                        
                        
                        for(int i=0; i<eElement.getElementsByTagName("generalization").getLength();i++){
                            Element e = (Element) nod.item(i);
                            String destino = e.getAttribute("general");
                            ligacao.setDestino(destino);
                            ligacao.setTipo(eElement.getElementsByTagName("generalization").item(i).getAttributes().getNamedItem("xmi:type").toString().replaceAll("xmi:type=", "").replaceAll("\"", "").replaceAll("uml:", ""));
                            li.add(ligacao);
                            a.setLigacao(li);
                            //System.out.println(ligacao.getTipo());
                        }
                        
                    }
                    
                    
                    //Adiciona a classe criada no arraylist
                    dados.add(a);
		}
	}
        
        
        //depois que preenche todas as classes cria-se um objeto do algoritmo para que possa funcionar
        AlgoritmoTabela a = new AlgoritmoTabela();
        //seta a lista preenchida
        a.setTable(dados);
        
        a.imprime();
        
    } catch (Exception e) {
	e.printStackTrace();
    }
  }
    
}
