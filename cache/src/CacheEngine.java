
import java.math.BigInteger;
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
    //Sets
    HashMap<String, ArrayList<CacheLine>> cacheSets;
    
    //Constructor
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

   //Procesar trace.
    public void processingAccess(int tipoInstruccion, String dirMemHex){
        
    }
    //Se obtiene la repsentacion de un hexadecimal en binario (32 bits)
    public String hexTo32Bits(String hex) {
        //String binAddr = Integer.toBinaryString(Integer.parseInt(hex, 16)); 
        //return String.format("%032", new BigInteger(binAddr));
        int i = Integer.parseInt(hex, 16);
        String bin = Integer.toBinaryString(i);
        return this.complete32Bits(bin);
    }
    //Le agrega 0s a un numero binario menor a 32 bits.
    public String complete32Bits(String bin){
        String completeBin = ""+bin;
        for(int i = bin.length(); i < 32; i++){
            completeBin = "0"+completeBin;
        }
        return completeBin;
    }
    //Maneja un write
    public  void handlingWrite(String sIndex, String sTag){
        
    }
    //Maneja un read
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
            this.numWordsCopiadosA+=bs;
        }
        this.numRefDatos++;
    }
    //Manejar un write miss
    public void handlingWriteMiss(String sIndex, String sTag, int indexCacheLine){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        if(wa){//Si es write allocate
            if(cls.get(indexCacheLine).isDirty()){//Si está dirty
                this.numWordsCopiadosA+=bs;
            }
            cls.get(indexCacheLine).setTag(sTag);
            cls.get(indexCacheLine).setDirty(true);
            this.numWordsCopiadosDesde+=bs;
            this.numRefDatos++;
        }
        else{//Si es write no allocate
            this.numWordsCopiadosA+=bs;
        }
        this.numFaltaDatos++;
    }
    //Manejar un read hit
    public void handlingReadHit(int tipoAcceso){
        if(tipoAcceso == 0){//Si es una lectura de dato.
            this.numRefDatos++;
        }
        else{//Si es una lectura de instrucción
            this.numRefInstr++;
        }
    }
    //Manejar un read miss
    public void handlingReadMiss(int tipoAcceso, String sIndex, String sTag, int indexCacheLine){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        if(cls.get(indexCacheLine).isDirty()){//Si esta dirty
                this.numWordsCopiadosA+=bs;
                cls.get(indexCacheLine).setDirty(false);
            }
        cls.get(indexCacheLine).setTag(sTag);
        this.numWordsCopiadosDesde+=bs;
        if(tipoAcceso == 0){//Si es una lectura de dato.
            this.numFaltaDatos++;
            this.numRefDatos++;
        }
        else{//Si es una lectura de instrucción
            this.numFaltaInstr++;
            this.numRefInstr++;
        }
    }
    public void calcularPartesAddress() {
    }
    
    public void imprimirResultado(){
        System.out.println("Numero referencias instrucciones: "+this.numRefInstr);
        System.out.println("Numero faltas instrucciones: "+this.numFaltaInstr);
        System.out.println("Numero referencias datos: "+this.numRefDatos);
        System.out.println("Numero faltas datos: "+this.numFaltaDatos);
        System.out.println("Numero  words copiados desde memoria: "+this.numWordsCopiadosDesde);
        System.out.println("Numero words copiados a memoria: "+this.numWordsCopiadosA);
    }





   
    
    
    
    
}
