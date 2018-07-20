package soul.utilities;

import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;
import soul.SOUL;
import soul.core.Core;

public class SoulUtilities {
	
	public static void connect(SOUL soul, PhilosophersStone stone) {
		
		if(PhilosophersStoneUtilities.isConnected(soul, stone))
			return;
		
		PhilosophersStoneUtilities.privatelyConnect(soul, stone);
		
		soul.cores.add(new Core());
	}
}