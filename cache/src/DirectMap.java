
import java.util.ArrayList;


/**
 *
 * @author Felipe Román
 */
class DirectMap extends CacheEngine {

    public DirectMap(int bs, int cs, boolean split, boolean wb, boolean wa, int numSets) {
        super(bs, cs, split, wb, wa, numSets);
        this.calcularPartesAddress();
    }
    @Override
    public void processingAccess(int tipoInstruccion, String dirMemHex ){
        //Convierto el hexadecimal a binario
        String address = this.hexTo32Bits(dirMemHex);
        //Obtener bits del index y tag
        String sTag = address.substring(0, this.tag-1);
        String sIndex = address.substring(this.tag, this.index-1);
        //Si existe el valor .
        if(this.cacheSets.containsKey(sIndex)){
            ArrayList<CacheLine> cl= this.cacheSets.get(sIndex);
            //Si la linea está vacía
            if(cl.isEmpty()){
                
            }
            else{
                
            }
        }
        else{
            
        }
    }
    @Override
    public void calcularPartesAddress() {
        this.offset = 2+(int)(Math.log(bs) / Math.log(2));
        this.index = (int)(Math.log(cs) / Math.log(2));
        this.tag = 32 - this.offset - this.index;
    }

    

    

    
    
}
