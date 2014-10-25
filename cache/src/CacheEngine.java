
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Felipe Román
 */
class CacheEngine {
    
    //Variables de Control
    int bs;
    int cs;
    boolean split;
    boolean wb;
    boolean wa;
    //Contadores de Salida
    int numRefInstr;
    int numRedDatos;
    int numFaltaInstr;
    int numFaltaDatos;
    int numWordsCopiadosDesde;
    int numWordsCopiadosA;
    //Componentes de la direccion
    int tag;
    int index;
    int offset;
    //Numero de sets
    int numSets;
    HashMap<String, ArrayList<CacheLine>> cacheSets;
    

    public CacheEngine(int bs, int cs, boolean split, boolean wb, boolean wa, int numSets) {
        this.bs = bs;
        this.cs = cs;
        this.split = split;
        this.wb = wb;
        this.wa = wa;
        this.numSets = numSets;
        this.numRefInstr = 0;
        this.numRedDatos = 0;
        this.numFaltaInstr = 0;
        this.numFaltaDatos = 0;
        this.numWordsCopiadosDesde = 0;
        this.numWordsCopiadosA = 0;
        this.cacheSets = new HashMap();
        
    }

   
    public void processingAccess(int tipoInstruccion, String dirMemHex ){
        
    }
    public String hexTo32Bits(String hex) {
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return this.complete32Bits(bin);
    }
    public String complete32Bits(String bin){
        String completeBin = ""+bin;
        for(int i = bin.length(); i <= 32; i++){
            completeBin = "0"+completeBin;
        }
        return completeBin;
    }
    public void handlingHit(){
        if(wb){
            //this.WriteBack();
        }
        else{
            //this.WriteThrough();
        }
    }
    public void handlingMiss(){
        if(wa){
            //this.WriteAllocate();
        }
        else{
            //this.WriteNoAllocate();
        }
    }
    public void calcularPartesAddress() {
    }





   
    
    
    
    
}
