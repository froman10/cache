
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
    int numRefDatos;
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
        this.numRefDatos = 0;
        this.numFaltaInstr = 0;
        this.numFaltaDatos = 0;
        this.numWordsCopiadosDesde = 0;
        this.numWordsCopiadosA = 0;
        this.cacheSets = new HashMap();
        
    }

   
    public void processingAccess(int tipoInstruccion, String dirMemHex){
        
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
    
    public  void handlingWrite(String sIndex, String sTag){
        
    }
    public void handlingRead(int tipoAcceso, String sIndex, String sTag){
        
    }
    //Manejar un write hit
    public void handlingWriteHit(String sIndex, String sTag, int indexCacheLine){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        if(wb){//Si es write back
            cls.get(indexCacheLine).setTag(sTag);
            cls.get(indexCacheLine).setDirty(true);         
        }
        else{//Si es write through
            cls.get(indexCacheLine).setTag(sTag);
            this.numWordsCopiadosA++;
        }
        this.numRefDatos++;
    }
    public void handlingWriteMiss(String sIndex, String sTag, int indexCacheLine){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        if(wa){//Si es write allocate
            if(cls.get(indexCacheLine).isDirty()){
                this.numWordsCopiadosA++;
            }
            cls.get(indexCacheLine).setTag(sTag);
            cls.get(indexCacheLine).setDirty(true);
            this.numWordsCopiadosDesde++;
        }
        else{//Si es write no allocate
            this.numWordsCopiadosA++;
        }
        this.numFaltaDatos++;
    }
    public void handlingReadHit(int tipoAcceso){
        if(tipoAcceso == 0){
            this.numRefDatos++;
        }
        else{
            this.numRefInstr++;
        }
    }
    public void handlingReadMiss(int tipoAcceso, String sIndex, String sTag, int indexCacheLine){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        if(cls.get(indexCacheLine).isDirty()){
                this.numWordsCopiadosA++;
                cls.get(indexCacheLine).setDirty(false);
            }
        cls.get(indexCacheLine).setTag(sTag);
        this.numWordsCopiadosDesde++;
        if(tipoAcceso == 0){
            this.numFaltaDatos++;
        }
        else{
            this.numFaltaInstr++;
        }
    }
    public void calcularPartesAddress() {
    }





   
    
    
    
    
}
