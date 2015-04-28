package abcd3901.graphics.sprite;

public class MetaSprite extends Sprite{

	
	
	
	
	
	Sprite[] sprites;
	
	/**
	 * Creates a meta sprite that contains multiple sprites instead of only one. The coolest thing is that is can be used as a sprite itself!
	 * @param sprites the stack (or array) of sprites contained within this MetaSprite.
	 */
	public MetaSprite(Sprite[] sprites) {
		super(validate(sprites));
		this.sprites = sprites;
		
	}
	
	/**
	 * Used by the constructor to validate the array length of the provided <code>sprites</code> array
	 * @param sprites the array of sprites to check.
	 * @return the first element of the array.
	 */
	private static Sprite validate(Sprite[] sprites){
		if(sprites.length == 0){
			throw new IllegalArgumentException(SpriteConstants.ERROR_MESSAGE_ARRAY_LENGHT_0);
		}
		return sprites[0];
	}
	
	
	@Override
	public Sprite getSprite(int meta) {
		if(meta>0&&meta<=sprites.length){
			return sprites[meta];
		}
		return super.getSprite();
	}

}
