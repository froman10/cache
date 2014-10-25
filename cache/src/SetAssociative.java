/**
 *
 * @author Felipe Román
 */
class SetAssociative extends CacheEngine {

    public SetAssociative(int bs, int cs, boolean split, boolean wb, boolean wa, int numSets) {
        super(bs, cs, split, wb, wa, numSets);
        this.calcularPartesAddress();
    }

    @Override
     public void calcularPartesAddress() {
        this.offset = 2+(int)(Math.log(bs) / Math.log(2));
        this.index = (int)(Math.log(cs) / Math.log(2));
        this.tag = 32 - this.offset - this.index;
    }

    
    

    
    
}
