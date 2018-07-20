package soul;

import java.util.ArrayList;

import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;
import soul.core.Core;

public class SOUL extends PhilosophersStone {
	
	public ArrayList<Core> cores = new ArrayList<Core>();
	
	public Object onCall(ArrayList<Object> packet) {
		
		for(int i = 0; i < privateConnections.size() && i < cores.size(); i++) {
						
			ArrayList<Object> newPacket = new ArrayList<Object>();
			
			for(int j = 0; j < packet.size(); j++)
				newPacket.add(cores.get(i).process("" + packet.get(j)));
				
			PhilosophersStoneUtilities.onCall(
					privateConnections.get(i),
					newPacket);
		}
		
		return null;
	}
}