/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Skorp
 */
class SetAssociative extends CacheHeart {

    int numSets;

    public SetAssociative(int numSets, int bs, int cs, boolean split, HitHandler hh, MissHandler mh) {
        super(bs, cs, split, hh, mh);
        this.numSets = numSets;
    }
    

    
    
}
