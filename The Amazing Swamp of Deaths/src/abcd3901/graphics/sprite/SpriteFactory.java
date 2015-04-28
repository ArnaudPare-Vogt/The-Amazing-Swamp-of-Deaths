package abcd3901.graphics.sprite;

import abcd3901.graphics.SpriteSheet;

/**
 * Class used to generate sprites.
 * @author Arnaud Paré-Vogt
 *
 */
public class SpriteFactory {

	
	public static Sprite[] getConnectedTexturesBase(SpriteSheet ss,int res){
		if(ss.getSize() != 8*res){
			throw new IllegalArgumentException(SpriteConstants.ERROR_MESSAGE_WRONG_SPRITESHEET_SIZE.replace("%s", ""+ss.getSize()) + 8*res);
		}
		
		Sprite[] baseTiles = new Sprite[13];
		for (int i = 0; i < baseTiles.length; i++) {
			int x = i%8;
			int y = i/8;
			baseTiles[i] = new Sprite(ss, x*res, y*res*4, res, res);
		}
		
		return baseTiles;
	}
}
