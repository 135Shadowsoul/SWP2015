package webot.logic;

import java.io.File;

public interface InterfaceLogicManager {

	/**
	 * Returns true if and only if the Logic was successfully loaded
	 * else false
	 * 
	 * @param origin
	 * @return true if and then if the Logic was successfully loaded else false
	 */
	public abstract boolean newLogicChosen(File origin);

	public abstract void playGameLogic();

	public abstract void reset();

	public abstract void stopPlaying();

}