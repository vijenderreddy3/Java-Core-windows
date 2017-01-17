package ai;


import java.util.Random;

import view.gameplay.HumanVSCom;


import model.CBoard;
import model.Const;

public abstract class Computer {
	int depth;
	Random random;
	int color;
	int enemyColor;
	int eval;
	HumanVSCom humanVSCom;
	
	public Computer(int depth, int color, int eval, HumanVSCom humanVSCom){
		this.depth = depth;
		this.color = color;
		this.enemyColor = color == Const.WHITE ? Const.BLACK : Const.WHITE;
		this.random = humanVSCom.random;
		this.eval = eval;
		this.humanVSCom = humanVSCom;
	}
	
	abstract public void nextAction(CBoard cBoard);
	
	public void setEval(int eval){
		this.eval = eval;
	}
	
	public void setDepth(int depth){
		this.depth = depth;
	}
}
