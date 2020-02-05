
public abstract class Movement{
    
    private Sprite sprite;
    
    public Movement(Sprite sprite){
        this.sprite = sprite;
    }
    
    public abstract void updatePos();
    
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
