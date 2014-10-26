
/**
 *
 * @author Felipe Román
 */
public class CacheLine {
    boolean dirty;
    String tag;

    public CacheLine() {
        this.dirty = false;
        this.tag = "";
    }
    public boolean equalToTag(String tagIn){
        return this.tag.equals(tagIn);
    }
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    
   
    
    
}
