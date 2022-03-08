package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Alberto Gutiérrez Morán
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("D:\\ULE\\3º\\SI\\datos_3.txt");

        /*ArrayList<Alfabeto> listaOrdenada = ordenar(lista);*/

        /** FUENTE DE INFORMACIÓN */
        ArrayList<Alfabeto> lista = generarLista(file);
        System.out.println("Entropía en bits: "+calcularEntropia(lista));

        //PRUEBA:
        /*int pos = checkExist(lista, 'd');
        System.out.println(lista.get(pos).imprimir());*/

        for(int i=0; i<lista.size(); i++){
            System.out.print(i+1 + " - ");
            System.out.println(lista.get(i).imprimir());
        }
    }

    private static ArrayList<Alfabeto> generarLista(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        ArrayList<Alfabeto> lista = new ArrayList<Alfabeto>();
        int total=0;

        while (true) {
            String nxt = sc.nextLine();
            for(int i=0; i<nxt.length(); i++){
                char c = nxt.charAt(i);
                if(c==' '){c='⎵';}
                int x = checkExist(lista, c);
                if(x==-1){
                    Alfabeto newletter = new Alfabeto(c);
                    lista.add(newletter);
                }else{
                    lista.get(x).aumentarFrecuencia();
                }
                total++;
            }

            if(sc.hasNextLine()){
                cambioDeLinea(lista);
                total+=2;
            }else{
                break;
            }
        }

        for(int i=0; i<lista.size(); i++){
            lista.get(i).setProbabilidad(total);
        }

        return lista;
    }

    private static int checkExist(ArrayList<Alfabeto> list, char c){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getChar()==c){ return i; }
        }

        return -1;
    }

    private static double calcularEntropia(ArrayList<Alfabeto> list){
        double ent = 0.0;
        for(int i=0; i<list.size(); i++){
            double prob = list.get(i).getProbabilidad();
            double log = (Math.log(1/prob) / Math.log(2));
            ent+= (prob * log);
        }
        return redondearDecimal(ent,5);
    }

    private static ArrayList<Alfabeto> ordenar(ArrayList<Alfabeto> list){
        ArrayList<Alfabeto> out = list;
        Alfabeto temp;

        for (int i=0; i<out.size(); i++) {
            for (int j=1; j<(out.size() - i); j++) {
                double p0 = out.get(j-1).getProbabilidad();
                double p1 = out.get(j).getProbabilidad();
                if (p1 > p0) {
                    temp = list.get(j-1);
                    list.set(j-1,list.get(j));
                    list.set(j,temp);
                }
            }
        }

        return out;
    }

    private static void cambioDeLinea(ArrayList<Alfabeto> list){
        char c='⎵';
        int x = checkExist(list, c);
        if(x==-1){
            Alfabeto newletter = new Alfabeto(c);
            newletter.aumentarFrecuencia();
            list.add(newletter);
        }else{
            list.get(x).aumentarFrecuencia();
            list.get(x).aumentarFrecuencia();
        }
    }

    private static double redondearDecimal(double x, int dec){
        double resultado = x;
        double parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, dec);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, dec))+parteEntera;
        return resultado;
    }

}


