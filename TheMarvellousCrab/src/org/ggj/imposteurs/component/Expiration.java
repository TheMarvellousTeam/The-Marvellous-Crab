package org.ggj.imposteurs.component;

import com.apollo.Component;



public class Expiration extends Component{

		private int initialLifeTime;
		private int remainingLifeTime;
		private Callback callback;
	public Expiration(int lifeTime, Callback callback) {
		this.remainingLifeTime = this.initialLifeTime = lifeTime;
		this.callback = callback;
	}
	public boolean isLiving(){
		return remainingLifeTime > 0;
	}
	public void decreaseRemainingLifeTime(int time){
		remainingLifeTime -= time;
		if(remainingLifeTime < 0)
			remainingLifeTime = 0;
	}
	public float getRatio(){
		return ((float)remainingLifeTime)/((float)initialLifeTime);
	}
	public void update(int delta){
		super.update(delta);
		decreaseRemainingLifeTime(delta);
		if(!isLiving()){
			if(callback != null)
				callback.onExpire();
			owner.getWorld().deleteEntity(owner);
		}
	}
	public interface Callback{
		public void onExpire();
	}

}
