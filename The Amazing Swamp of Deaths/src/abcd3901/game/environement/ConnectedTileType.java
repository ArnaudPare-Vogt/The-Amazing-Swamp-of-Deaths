package abcd3901.game.environement;

import abcd3901.graphics.sprite.MetaSprite;
import abcd3901.graphics.sprite.Sprite;
import abcd3901.utility.math.ArrayUtil;

public class ConnectedTileType extends TileType{

	public ConnectedTileType(MetaSprite s,String name,boolean isGround) {
		super(s,name,isGround);
	}
	
	@Override
	public Sprite getSprite(TileType[] adjacentTiles) {
		boolean[] st = new boolean[adjacentTiles.length];
		for (int i = 0; i < st.length; i++) {
			st[i] = adjacentTiles[i].equals(this);
		}
		int closeCount = ArrayUtil.count(st[1],st[3],st[4],st[6]);
		int farCount = ArrayUtil.count(st[0],st[2],st[5],st[7]);
		//return sprite.getSprite(14);
		
		//block of DOOM incoming!
		if(closeCount == 0){
			return this.sprite.getSprite(0);//middle island
		}else if(closeCount == 1){
			if(st[1])return sprite.getSprite(16);//top
			if(st[3])return sprite.getSprite(13);//left
			if(st[4])return sprite.getSprite(15);//right
			if(st[6])return sprite.getSprite(14);//bottom
			else return this.sprite.getSprite(0);//middle island
		}else if(closeCount == 2){
			if(st[3] && st[4])return this.sprite.getSprite(3);//vertical line
			if(st[1] && st[6])return this.sprite.getSprite(4);//horizontal line
			if(st[3] && st[6]){
				if(st[5]) return this.sprite.getSprite(17);
				else return this.sprite.getSprite(5);//
			}
			if(st[6] && st[4]){
				if(st[7])return this.sprite.getSprite(18);
				else return this.sprite.getSprite(6);//
			}
			if(st[1] && st[4]){
				if(st[2])return this.sprite.getSprite(19);
				else return this.sprite.getSprite(7);//└
			}
			if(st[1] && st[3]){
				if(st[0])return this.sprite.getSprite(20);
				else return this.sprite.getSprite(8);//
			}
		}else if(closeCount == 3){
			if(st[1] && st[3] && st[4]){
				if(st[0] && st[2])return this.sprite.getSprite(24);
				else if(st[0])return this.sprite.getSprite(43);
				else if(st[2])return this.sprite.getSprite(45);
				else return this.sprite.getSprite(9);//
			}
			if(st[1] && st[3] && st[6]){
				if(st[0] && st[5])return this.sprite.getSprite(21);
				else if(st[0])return this.sprite.getSprite(46);
				else if(st[5])return this.sprite.getSprite(44);
				else return this.sprite.getSprite(10);//
			}
			if(st[3] && st[4] && st[6]){
				if(st[7] && st[5])return this.sprite.getSprite(22);
				else if(st[7])return this.sprite.getSprite(41);
				else if(st[5])return this.sprite.getSprite(47);
				else return this.sprite.getSprite(11);//
			}
			if(st[1] && st[4] && st[6]){
				if(st[2] && st[7])return this.sprite.getSprite(23);
				else if(st[2])return this.sprite.getSprite(42);
				else if(st[7])return this.sprite.getSprite(48);
				else return this.sprite.getSprite(12);//
			}
		}else if(closeCount == 4){
			if(farCount ==0){
				return sprite.getSprite(1);//cross
			}else if (farCount == 1){
				if(st[2])return this.sprite.getSprite(33);
				if(st[0])return this.sprite.getSprite(34);
				if(st[5])return this.sprite.getSprite(35);
				if(st[7])return this.sprite.getSprite(36);
			}else if(farCount == 2){
				if(st[2] && st[7])return this.sprite.getSprite(29);
				if(st[0] && st[2])return this.sprite.getSprite(30);
				if(st[5] && st[0])return this.sprite.getSprite(31);
				if(st[7] && st[5])return this.sprite.getSprite(32);
				if(st[2] && st[5])return this.sprite.getSprite(37);
				if(st[7] && st[0])return this.sprite.getSprite(38);
			}else if (farCount == 3){
				if(!st[0])return this.sprite.getSprite(25);
				if(!st[5])return this.sprite.getSprite(26);
				if(!st[7])return this.sprite.getSprite(27);
				if(!st[2])return this.sprite.getSprite(28);
			}else if(farCount == 4){
				return sprite.getSprite(2);//fill
			}
			
		}
		
		return super.getSprite(adjacentTiles);
		
	}

}
