
import java.util.ArrayList;

/**
 *
 * @author Felipe Román
 */
class SetAssociative extends CacheEngine {

    public SetAssociative(int bs, int cs, boolean split, boolean wb, boolean wa, int numSets) {
        super(bs, cs, split, wb, wa, numSets);
        this.calcularPartesAddress();
    }

    //Procesar trace con Set Associative.
    @Override
    public void processingAccess(int tipoAcceso, String dirMemHex ){
        //Convierto el hexadecimal a binario
        String address = this.hexTo32Bits(dirMemHex);
        //Obtener bits del index y tag
        String sTag = address.substring(0, this.tag);
        String sIndex = address.substring(this.tag, this.tag+this.index);
        //Si no existe el set, lo creamos y le agregamos una linea vacía.
        if(!this.cacheSets.containsKey(sIndex)){
            ArrayList<CacheLine> cls = new ArrayList<>();
            cls.add(new CacheLine());
            this.cacheSets.put(sIndex, cls);
        }
        System.out.println(tipoAcceso+" "+address+" "+sTag+" "+sIndex+" "+this.cacheSets.get(sIndex).get(0).equalToTag(sTag)+" "+this.cacheSets.get(sIndex).get(0).isDirty());
        //Elegimos el tipo de acción. 
        if(tipoAcceso == 0 || tipoAcceso == 2){//Leer
            this.handlingRead(tipoAcceso, sIndex, sTag);
        }
        else{//Escribir
            this.handlingWrite(sIndex, sTag);
        }
        
    }

    //Elegir proceso de hit o miss en escritura.
    @Override
    public  void handlingWrite(String sIndex, String sTag){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        int lineIndex;
        //Verificamos si el tag corresponde a alguna de las líneas ya ingresadas.
        for(lineIndex = 0; lineIndex < cls.size(); lineIndex++){
            if(cls.get(lineIndex).equalToTag(sTag)){//Si coincide el tag es un write hit.
                this.handlingWriteHit(sIndex, sTag, lineIndex);
                return;
            }  
        }
        //Verificamos que quede espacio para agregar una nueva línea.
        if(lineIndex < this.numSets){
            cls.add(new CacheLine());
            this.handlingWriteMiss(sIndex, sTag, lineIndex);
        }
        //Reemplazamos en orden FIFO
        else{
            CacheLine cl = cls.get(0);
            cls.remove(0);
            cls.add(cl);
            this.handlingWriteMiss(sIndex, sTag, --lineIndex);
        }
    }
    //Elegir proceso de hit o miss en lectura.
    @Override
    public void handlingRead(int tipoAcceso, String sIndex, String sTag){
        ArrayList<CacheLine> cls = this.cacheSets.get(sIndex);
        int lineIndex;
        //Verificamos si el tag corresponde a alguna de las líneas ya ingresadas.
        for(lineIndex = 0; lineIndex < cls.size(); lineIndex++){
            if(cls.get(lineIndex).equalToTag(sTag)){//Si coincide el tag es un write hit.
                this.handlingReadHit(tipoAcceso);
                return;
            }  
        }
        //Verificamos que quede espacio para agregar una nueva línea.
        if(lineIndex < this.numSets){
            cls.add(new CacheLine());
            this.handlingReadMiss(tipoAcceso, sIndex, sTag, lineIndex);
        }
        //Reemplazamos en orden FIFO
        else{
            CacheLine cl = cls.get(0);
            cls.remove(0);
            cls.add(cl);
            this.handlingReadMiss(tipoAcceso, sIndex, sTag, --lineIndex);
        }
    }

    
    

    
    
}
