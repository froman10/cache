
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
    int tag;
    int numSets;
    HashMap cache;
    

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
        this.cache = new HashMap();
        this.calcularTag();
    }

   
    public void processingAccess(int tipoInstruccion, String dirMemHex ){
        
    }
    
    public void handlingHit(){
        if(wb){
            this.WriteBack();
        }
        else{
            this.WriteThrough();
        }
    }
    public void handlingMiss(){
        if(wa){
            this.WriteAllocate();
        }
        else{
            this.WriteNoAllocate();
        }
    }
    public void calcularTag(){
        tag =  32 - (int)(Math.log(bs) / Math.log(2));
    }

    private void WriteBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void WriteThrough() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void WriteAllocate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void WriteNoAllocate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
