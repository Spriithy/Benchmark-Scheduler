package src.prog.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeInstruction {

	public boolean[] instr;

	public ListeInstruction(int n, int m) {
		List<Boolean> bls = new ArrayList<>();
		for (int i = 0; i < n; i++)
			bls.add(i < m);
		Collections.shuffle(bls);
		instr = new boolean[n];
		for (int i = 0; i < n; i++)
			instr[i] = bls.get(i);
	}

}
