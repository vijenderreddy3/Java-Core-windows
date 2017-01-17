package ai;
import java.util.ArrayList;
import java.util.Iterator;

import view.gameplay.HumanVSCom;

import model.CBoard;
import model.CPiece;
import model.Const;
import model.Coord;
import model.process.CBProcess;

public class ComAlphaBeta extends Computer {
	//private ArrayList<>
	public ComAlphaBeta(int depth, int color, int eval, HumanVSCom humanVSCom) {
		super(depth, color, eval, humanVSCom);
	}
	@Override
	public void nextAction(CBoard cBoard) {	
		// Equivalent maxsearch
		// Generate the next set of states
		// Update of the current moves the co White
		// Update the table bit White
		if(color == Const.WHITE){	
			ArrayList<Coord> list = CBProcess.generate(cBoard, Const.WHITE);	
			ArrayList<Coord> finalList = new ArrayList<Coord>();
			Iterator<Coord> it = list.iterator();
			Coord from;
			Coord to;
			CPiece bkCPiece;
			boolean bkFirstMoved;
			int bkType;
			int val;
			int alpha = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			while(it.hasNext()){
				from = it.next();
				to = it.next();
				bkCPiece = cBoard.slot(to).cPiece;
				bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
				bkType = cBoard.slot(from).cPiece.type;
				CBProcess.move(cBoard, from, to);
				if(bkCPiece != null)
					if(bkCPiece.type == Const.KING){
						finalList.clear();
						finalList.add(from);
						finalList.add(to);
						CBProcess.move(cBoard, to, from);
						if(bkCPiece != null){
							cBoard.slot[to.x][to.y].cPiece = bkCPiece;
							cBoard.cArray[Const.BLACK].add(bkCPiece);
						}
						cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
						cBoard.slot(from).cPiece.type = bkType;
						break;
					}
				val = minSearch(1, cBoard, alpha, beta);
				if(val > alpha){
					alpha = val;
					finalList.clear();
					finalList.add(from);
					finalList.add(to);
				}
				else if(val == alpha && val != Integer.MIN_VALUE){
					finalList.add(from);
					finalList.add(to);
				}
				CBProcess.move(cBoard, to, from);
				if(bkCPiece != null){
					cBoard.slot[to.x][to.y].cPiece = bkCPiece;
					cBoard.cArray[Const.BLACK].add(bkCPiece);
				}
				cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
				cBoard.slot(from).cPiece.type = bkType;
			}
			if(finalList.size() == 0)
				return;
			int choose = random.nextInt(finalList.size() / 2) * 2;	
			it = finalList.iterator();
			for(int i = 0; i < choose; i++)
				it.next();
			from = it.next();
			to = it.next();
			humanVSCom.coordEnemyMoved = to;
			humanVSCom.listUndo.push(Const.NORMAL);
			humanVSCom.listUndo.push(cBoard.slot(from).cPiece.firstMoved);
			humanVSCom.listUndo.push(humanVSCom.isEnemySelect);
			humanVSCom.listUndo.push(cBoard.slot(to).cPiece);
			humanVSCom.listUndo.push(to);
			humanVSCom.listUndo.push(from);
			CBProcess.finalMove(cBoard, from, to, humanVSCom);
			humanVSCom.ways[Const.WHITE].add(from + " ---> " + to);
			humanVSCom.lstWays[Const.WHITE].setListData(humanVSCom.ways[Const.WHITE]);
		}
		else {
			ArrayList<Coord> list = CBProcess.generate(cBoard, Const.BLACK);
			ArrayList<Coord> finalList = new ArrayList<Coord>();
			Iterator<Coord> it = list.iterator();
			Coord from;
			Coord to;
			CPiece bkCPiece;
			boolean bkFirstMoved;
			int bkType;
			int val;
			int alpha = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			while(it.hasNext()){
				from = it.next();
				to = it.next();
				bkCPiece = cBoard.slot(to).cPiece;
				bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
				bkType = cBoard.slot(from).cPiece.type;
				CBProcess.move(cBoard, from, to);
				if(bkCPiece != null)
					if(bkCPiece.type == Const.KING){
						finalList.clear();
						finalList.add(from);
						finalList.add(to);
						CBProcess.move(cBoard, to, from);
						if(bkCPiece != null){
							cBoard.slot[to.x][to.y].cPiece = bkCPiece;
							cBoard.cArray[Const.WHITE].add(bkCPiece);
						}
						cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
						cBoard.slot(from).cPiece.type = bkType;
						break;
					}
				val = maxSearch(1, cBoard, alpha, beta);
				if(val < beta){
					beta = val;
					finalList.clear();
					finalList.add(from);
					finalList.add(to);
				}
				else if(val == beta && val != Integer.MAX_VALUE){
					finalList.add(from);
					finalList.add(to);
				}
				CBProcess.move(cBoard, to, from);
				if(bkCPiece != null){
					cBoard.slot[to.x][to.y].cPiece = bkCPiece;
					cBoard.cArray[Const.WHITE].add(bkCPiece);
				}
				cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
				cBoard.slot(from).cPiece.type = bkType;
			}
			
			if(finalList.size() == 0)
				return;
			
			int choose = random.nextInt(finalList.size() / 2) * 2;	// lay vi tri from
			it = finalList.iterator();
			for(int i = 0; i < choose; i++)
				it.next();
			
			from = it.next();
			to = it.next();
			humanVSCom.coordEnemyMoved = to;
			humanVSCom.listUndo.push(cBoard.slot(from).cPiece.firstMoved);
			humanVSCom.listUndo.push(humanVSCom.isEnemySelect);
			humanVSCom.listUndo.push(cBoard.slot(to).cPiece);
			humanVSCom.listUndo.push(to);
			humanVSCom.listUndo.push(from);
			humanVSCom.listUndo.push(Const.NORMAL);
			humanVSCom.panelChessBoard.animate(from, to, cBoard.slot(from).cPiece.type, cBoard.slot(from).cPiece.color);
			CBProcess.finalMove(cBoard, from, to, humanVSCom);
			humanVSCom.ways[Const.BLACK].add(from + " ---> " + to);
			humanVSCom.lstWays[Const.BLACK].setListData(humanVSCom.ways[Const.BLACK]);
		}
	}
	
	public int maxSearch(int depth, CBoard cBoard, int alpha, int beta){
		if(depth >= this.depth){
			if(eval != Const.EVAL_V)
				CBProcess.refresh(cBoard);
			
			return Evaluation.cal(cBoard, Const.BLACK, eval);
		}
		
		ArrayList<Coord> list = CBProcess.generate(cBoard, Const.WHITE);
		Iterator<Coord> it = list.iterator();
		
		Coord from;
		Coord to;
		
		CPiece bkCPiece;
		boolean bkFirstMoved;
		int bkType;
		
		int max = Integer.MIN_VALUE;
		
		while(it.hasNext()){
			from = it.next();
			to = it.next();
			
			bkCPiece = cBoard.slot[to.x][to.y].cPiece;
			bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
			bkType = cBoard.slot(from).cPiece.type;			
			CBProcess.move(cBoard, from, to);
			
			if(depth == 1 && bkCPiece != null)
				if(bkCPiece.type == Const.KING ){
					CBProcess.move(cBoard, to, from);
					if(bkCPiece != null){
						cBoard.slot[to.x][to.y].cPiece = bkCPiece;
						cBoard.cArray[Const.BLACK].add(bkCPiece);
					}
					
					cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
					cBoard.slot(from).cPiece.type = bkType;
					
					return Integer.MAX_VALUE;
				}
			
			max = Math.max(max, minSearch(depth + 1, cBoard, alpha, beta));
			
			CBProcess.move(cBoard, to, from);
			if(bkCPiece != null){
				cBoard.slot[to.x][to.y].cPiece = bkCPiece;
				cBoard.cArray[Const.BLACK].add(bkCPiece);
			}
			
			cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
			cBoard.slot(from).cPiece.type = bkType;
			
			if(max > beta)
				return max;
			alpha = Math.max(alpha, max);
		}
		
		return max;
	}
	
	public int minSearch(int depth, CBoard cBoard, int alpha, int beta){
		if(depth >= this.depth){
			if(eval != Const.EVAL_V)
				CBProcess.refresh(cBoard);
			
			return Evaluation.cal(cBoard, Const.WHITE, eval);
		}
		
		ArrayList<Coord> list = CBProcess.generate(cBoard, Const.BLACK);
		Iterator<Coord> it = list.iterator();
		
		Coord from;
		Coord to;
		
		CPiece bkCPiece;
		boolean bkFirstMoved;
		int bkType;
		
		int min = Integer.MAX_VALUE;
		
		while(it.hasNext()){
			from = it.next();
			to = it.next();
			
			bkCPiece = cBoard.slot[to.x][to.y].cPiece;
			bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
			bkType = cBoard.slot(from).cPiece.type;
			CBProcess.move(cBoard, from, to);
			
			if(depth == 1 && bkCPiece != null)
				if(bkCPiece.type == Const.KING ){
					
					CBProcess.move(cBoard, to, from);
					if(bkCPiece != null){
						cBoard.slot[to.x][to.y].cPiece = bkCPiece;
						cBoard.cArray[Const.WHITE].add(bkCPiece);
					}
					
					cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
					cBoard.slot(from).cPiece.type = bkType;
					
					return Integer.MIN_VALUE;
				}
			
			min = Math.min(min, maxSearch(depth + 1, cBoard, alpha, beta));
			
			CBProcess.move(cBoard, to, from);
			if(bkCPiece != null){
				cBoard.slot[to.x][to.y].cPiece = bkCPiece;
				cBoard.cArray[Const.WHITE].add(bkCPiece);
			}
			
			cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
			cBoard.slot(from).cPiece.type = bkType;
			
			if(min < alpha)
				return min;
			beta = Math.min(beta, min);
		}
		
		return min;
	}
}
