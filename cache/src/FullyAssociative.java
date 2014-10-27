
import java.util.ArrayList;

/**
 *
 * @author Felipe Román
 */
class FullyAssociative extends CacheEngine {

    ArrayList<CacheLine> cls;//Un set gigante.
    public FullyAssociative(int bs, int cs, boolean split, boolean wb, boolean wa, int numSets) {
        super(bs, cs, split, wb, wa, numSets);
        this.cls = new ArrayList<>();
        this.calcularPartesAddress();
    }

    //Procesar trace con Set Associative.
    @Override
    public void processingAccess(int tipoAcceso, String dirMemHex ){
        //Convierto el hexadecimal a binario
        String address = this.hexTo32Bits(dirMemHex);
        //Obtener bits del index y tag
        String sTag = address.substring(0, this.tag);
        String sIndex = "";
        //Si no existe el set, lo creamos y le agregamos una linea vacía.
        if(this.cls.isEmpty()){
            cls.add(new CacheLine());
        }
        System.out.println(tipoAcceso+" "+address+" "+sTag+" "+sIndex+" "+this.cls.get(0).equalToTag(sTag)+" "+this.cls.get(0).isDirty());

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
        int lineIndex;
        //Verificamos si el tag corresponde a alguna de las líneas ya ingresadas.
        for(lineIndex = 0; lineIndex < this.cls.size(); lineIndex++){
            if(this.cls.get(lineIndex).equalToTag(sTag)){//Si coincide el tag es un write hit.
                this.handlingWriteHit(sIndex, sTag, lineIndex);
                return;
            }  
        }
        //Verificamos que quede espacio para agregar una nueva línea.
        if(lineIndex < this.numSets){
            this.cls.add(new CacheLine());
            this.handlingWriteMiss(sIndex, sTag, lineIndex);
        }
        //Reemplazamos en orden FIFO
        else{
            CacheLine cl = this.cls.get(0);
            this.cls.remove(0);
            this.cls.add(cl);
            this.handlingWriteMiss(sIndex, sTag, --lineIndex);
        }
    }
    //Elegir proceso de hit o miss en lectura.
    @Override
    public void handlingRead(int tipoAcceso, String sIndex, String sTag){
        int lineIndex;
        //Verificamos si el tag corresponde a alguna de las líneas ya ingresadas.
        for(lineIndex = 0; lineIndex < this.cls.size(); lineIndex++){
            if(this.cls.get(lineIndex).equalToTag(sTag)){//Si coincide el tag es un write hit.
                this.handlingReadHit(tipoAcceso);
                return;
            }  
        }
        //Verificamos que quede espacio para agregar una nueva línea.
        if(lineIndex < this.numSets){
            this.cls.add(new CacheLine());
            this.handlingReadMiss(tipoAcceso, sIndex, sTag, lineIndex);
        }
        //Reemplazamos en orden FIFO
        else{
            CacheLine cl = this.cls.get(0);
            this.cls.remove(0);
            this.cls.add(cl);
            this.handlingReadMiss(tipoAcceso, sIndex, sTag, --lineIndex);
        }
    }
//Maneja un write hit
    @Override
    public void handlingWriteHit(String sIndex, String sTag, int indexCacheLine){
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
    //Maneja un write miss
    @Override
    public void handlingWriteMiss(String sIndex, String sTag, int indexCacheLine){
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

    //Manejar un read miss
    @Override
    public void handlingReadMiss(int tipoAcceso, String sIndex, String sTag, int indexCacheLine){
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
    
}
