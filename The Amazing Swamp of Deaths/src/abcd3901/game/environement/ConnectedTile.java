package abcd3901.game.environement;

import abcd3901.graphics.sprite.MetaSprite;
import abcd3901.graphics.sprite.Sprite;
import abcd3901.utility.math.ArrayUtil;

public class ConnectedTile extends Tile{

	public ConnectedTile(MetaSprite s) {
		super(s);
	}
	
	@Override
	public Sprite getSprite(Tile[] adjacentTiles) {
		boolean[] st = new boolean[adjacentTiles.length];
		for (int i = 0; i < st.length; i++) {
			st[i] = adjacentTiles[i].equals(this);
		}
		int closeCount = ArrayUtil.count(st[1],st[3],st[4],st[6]);
		int farCount = ArrayUtil.count(st[0],st[2],st[5],st[7]);
		//return sprite.getSprite(14);
		
		//block of DOOM incoming!
		
		if(closeCount == 0){
			return this.sprite.getSprite(0);
		}else if(closeCount == 1){
			if(st[1])return sprite.getSprite(16);
			if(st[3])return sprite.getSprite(13);
			if(st[4])return sprite.getSprite(15);
			if(st[6])return sprite.getSprite(14);
		}
		return super.getSprite(adjacentTiles);
		
	}

}
