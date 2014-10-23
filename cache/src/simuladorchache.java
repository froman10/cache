
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Román
 */
public class simuladorchache {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        int bs = 0;
        int cs = 0;
        int numSets = 0;
        boolean split = false;
        int tipoCache = 1; //Direct Map Default 
        HitHandler hh = new WriteBack();
        MissHandler mh = new WriteAllocate();
        CacheHeart ch;
        
        for(int i = 0; i < args.length;i++){
            switch (args[i]) {
                case "-bs":
                    bs = Integer.parseInt(args[++i]);
                    break;
                case "-cs":
                    cs = Integer.parseInt(args[++i]);
                    break;
                case "-wt":
                    hh = new WriteThrough();
                    break;
                case "-fa":
                    tipoCache = 2;
                    break;                    
                case "-sa":
                    tipoCache = 3;
                    numSets = Integer.parseInt(args[++i]);
                    break;    
                case "-wna":
                    mh = new WriteNoAllocate();
                    break;
                case "-split":
                    split = true;
                    break;
            }
        }
        if(tipoCache == 1){
            ch = new DirectMap(bs, cs, split, hh, mh);
        }
        else if(tipoCache == 2){
            ch = new SetAssociative(numSets, bs, cs, split, hh, mh);
        }
        else if(tipoCache == 3){
            ch = new FullyAssociative(bs, cs, split, hh, mh);
        }
        try {
            BufferedReader bf = new BufferedReader(new FileReader(args[args.length-1]));
            String traceLine;
            String[] valuesLine;
            while ((traceLine = bf.readLine())!=null) {//Leemos la linea del archivo trace
                valuesLine = traceLine.split(" ");//Obtenemos los valores individuales. Nos interesan los dos primeros
                
             }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(simuladorchache.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(simuladorchache.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }
}
