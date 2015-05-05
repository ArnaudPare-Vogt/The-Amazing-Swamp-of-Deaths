package abcd3901.graphics.sprite;

import abcd3901.graphics.SpriteSheet;
import abcd3901.utility.math.ArrayUtil;

/**
 * Class used to generate sprites.
 * @author Arnaud Paré-Vogt
 *
 */
public class SpriteFactory {

	private static int[] duplicationOrder = {3,1,0,9};
	private static final String GENERATION_ERROR = "The generation has failed";
	
	
	public static MetaSprite getConnectectedTextures(SpriteSheet ss,int res){
		Sprite[] sp = getConnectedTexturesBase(ss,res);
		Sprite[] sprites = new Sprite[41];
		if(sp.length == ArrayUtil.intArraySum(duplicationOrder)){
			int index = 0;
			for (int i = 0; i < duplicationOrder.length; i++) {
				for (int j = 0; j < duplicationOrder[i]; j++) {
					Sprite s = new Sprite(sp[(ArrayUtil.intArraySumUntil(duplicationOrder, i))+j]);
					for (int k = 0; k < i+1; k++) {
						sprites[index] = new Sprite(s);
						s.rotateLeft();
						index++;
					}
				}
			}
			return new MetaSprite(sprites);
			
		}else{
			throw new IllegalStateException(GENERATION_ERROR);
		}
	}
	
	public static Sprite[] getConnectedTexturesBase(SpriteSheet ss,int res){
		if(ss.getSize() != 8*res){
			throw new IllegalArgumentException(SpriteConstants.ERROR_MESSAGE_WRONG_SPRITESHEET_SIZE.replace("%s", ""+ss.getSize()) + 8*res);
		}
		
		Sprite[] baseTiles = new Sprite[ArrayUtil.intArraySum(duplicationOrder)];
		for (int i = 0; i < baseTiles.length; i++) {
			int x = (i+1)%8;
			int y = (i+1)/8;
			baseTiles[i] = new Sprite(ss, x*res, y*res*4, res, res);
		}
		
		return baseTiles;
	}
}
