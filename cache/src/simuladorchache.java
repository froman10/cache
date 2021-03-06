
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Román
 */
public class simuladorchache {


    public static void main(String[] args) throws IOException{
        int bs = 0;
        int cs = 0;
        int numSets = 1;
        boolean split = false;
        int tipoCache = 1; //Direct Map Default 
        boolean wb = true;
        boolean wa = true;
        CacheEngine ch = null;
        
        Scanner sc = new Scanner(System.in);
        args = sc.nextLine().split(" ");
        
        for(int i = 0; i < args.length;i++){
            switch (args[i]) {
                case "-bs":
                    bs = Integer.parseInt(args[++i]);
                    break;
                case "-cs":
                    cs = Integer.parseInt(args[++i]);
                    break;
                case "-wt":
                    wb = false;
                    break;
                case "-fa":
                    tipoCache = 3;
                    break;                    
                case "-sa":
                    tipoCache = 2;
                    numSets = Integer.parseInt(args[++i]);
                    break;    
                case "-wna":
                    wa = false;
                    break;
                case "-split":
                    split = true;
                    break;
            }
        }
        if(tipoCache == 1){
            ch = new DirectMap(bs, cs, split, wb, wa, numSets);
        }
        else if(tipoCache == 2){
            ch = new SetAssociative(bs, cs, split, wb, wa, numSets);
        }
        else if(tipoCache == 3){
            numSets = cs;
            ch = new FullyAssociative(bs, cs, split, wb, wa, numSets);
        }
        try {
            BufferedReader bf = new BufferedReader(new FileReader(args[args.length-1]));
            String traceLine;
            String[] valuesLine;
            while ((traceLine = bf.readLine())!=null) {//Leemos la linea del archivo trace
                valuesLine = traceLine.split(" ");//Obtenemos los valores individuales. Nos interesan los dos primeros
                ch.processingAccess(Integer.parseInt(valuesLine[0]), valuesLine[1]);
                
             }
            ch.imprimirResultado();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(simuladorchache.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(simuladorchache.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
}
