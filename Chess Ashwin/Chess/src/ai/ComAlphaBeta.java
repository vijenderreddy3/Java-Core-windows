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
	public void nextAction(CBoard cBoard) {	// tuong duong maxsearch
		// Sinh ra tap cac trang thai ke tiep
		// Cap nhat so nuoc di hien tai cua cac quan co WHite
		// cap nhat lai table bit White
		if(color == Const.WHITE){
			// danh sach cac nuoc co the di
			ArrayList<Coord> list = CBProcess.generate(cBoard, Const.WHITE);
			
			// danh sach cac nuoc dung de chon sang trang thai ke tiep
			ArrayList<Coord> finalList = new ArrayList<Coord>();
			
			// iterator dung de duyet danh sach cac nuoc co the di
			Iterator<Coord> it = list.iterator();
			
			// toa do ban dau va toa do dich trong 1 nuoc di, lay tu list
			Coord from;
			Coord to;
			
			// cac bien dung de backup
			
			// bien de luu quan o slot(to), backup lai khi o slot do co quan dich bi an
			CPiece bkCPiece;
			
			// backup lai trang thai lan dau di chuyen
			boolean bkFirstMoved;
			
			// backup lai kieu quan o slot(from), backup lai khi quan do phong hau
			int bkType;
			
			// gia tri tinh toan
			int val;
			int alpha = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			
			while(it.hasNext()){
				// toa do bat dau di chuyen
				from = it.next();
				// toa do toi
				to = it.next();
				
				// backup quan o vi tri se den
				bkCPiece = cBoard.slot(to).cPiece;
				bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
				bkType = cBoard.slot(from).cPiece.type;
				
				// di chuyen quan co de tao trang thai moi
				CBProcess.move(cBoard, from, to);
				
				// xet truong hop an duoc luon quan vua cua dich neu an duoc luon thi thoat ngay
				if(bkCPiece != null)
					if(bkCPiece.type == Const.KING){
						finalList.clear();
						finalList.add(from);
						finalList.add(to);
						
						// restore lai ban co
						CBProcess.move(cBoard, to, from);
						if(bkCPiece != null){
							cBoard.slot[to.x][to.y].cPiece = bkCPiece;
							cBoard.cArray[Const.BLACK].add(bkCPiece);
						}
						cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
						cBoard.slot(from).cPiece.type = bkType;
						break;
					}
				
				// tinh toan de quy
				val = minSearch(1, cBoard, alpha, beta);
				
				// lam moi danh sach neu co gia tri tot hon
				if(val > alpha){
					alpha = val;
					finalList.clear();
					finalList.add(from);
					finalList.add(to);
				}
				// lay cac nuoc di co the dung lam trang thai moi
				// voi dieu kien nuoc di do khong dan den vua bi chet ( gia tri tra ve la MIN_VALUE o ham Minsearch)
				else if(val == alpha && val != Integer.MIN_VALUE){
					finalList.add(from);
					finalList.add(to);
				}
				
				// restore lai ban co
				CBProcess.move(cBoard, to, from);
				if(bkCPiece != null){
					cBoard.slot[to.x][to.y].cPiece = bkCPiece;
					cBoard.cArray[Const.BLACK].add(bkCPiece);
				}
				cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
				cBoard.slot(from).cPiece.type = bkType;
			}
			
			// neu list di chuyen rong thi return ra ngoai, nghia la ben com da bi het nuoc di
			// dieu nay xay ra khi quan king la quan cuoi cung bi an
			if(finalList.size() == 0)
				return;
			
			// lay ngau nhien mot cap trong finalList
			int choose = random.nextInt(finalList.size() / 2) * 2;	// lay vi tri from
			it = finalList.iterator();
			for(int i = 0; i < choose; i++)
				it.next();
			
			from = it.next();
			to = it.next();
			
			// nuoc di duoc chon, dung de ve
			humanVSCom.coordEnemyMoved = to;
			// backup nuoc di cho viec undo
			humanVSCom.listUndo.push(Const.NORMAL);
			humanVSCom.listUndo.push(cBoard.slot(from).cPiece.firstMoved);
			humanVSCom.listUndo.push(humanVSCom.isEnemySelect);
			humanVSCom.listUndo.push(cBoard.slot(to).cPiece);
			humanVSCom.listUndo.push(to);
			humanVSCom.listUndo.push(from);
			// di chuyen
			CBProcess.finalMove(cBoard, from, to, humanVSCom);
			// cap nhat vao danh sach di chuyen
			humanVSCom.ways[Const.WHITE].add(from + " ---> " + to);
			humanVSCom.lstWays[Const.WHITE].setListData(humanVSCom.ways[Const.WHITE]);
		}
		// com la ben den
		else {
			// danh sach cac nuoc co the di
			ArrayList<Coord> list = CBProcess.generate(cBoard, Const.BLACK);
			
			// danh sach cac nuoc dung de chon sang trang thai ke tiep
			ArrayList<Coord> finalList = new ArrayList<Coord>();
			
			// iterator dung de duyet danh sach cac nuoc co the di
			Iterator<Coord> it = list.iterator();
			
			// toa do ban dau va toa do dich trong 1 nuoc di, lay tu list
			Coord from;
			Coord to;
			
			// cac bien dung de backup
			
			// bien de luu quan o slot(to), backup lai khi o slot do co quan dich bi an
			CPiece bkCPiece;
			
			// backup lai trang thai lan dau di chuyen
			boolean bkFirstMoved;
			
			// backup lai kieu quan o slot(from), backup lai khi quan do phong hau
			int bkType;
			
			// gia tri tinh toan
			int val;
			int alpha = Integer.MIN_VALUE;
			int beta = Integer.MAX_VALUE;
			
			while(it.hasNext()){
				// toa do bat dau di chuyen
				from = it.next();
				// toa do toi
				to = it.next();
				
				// backup quan o vi tri se den
				bkCPiece = cBoard.slot(to).cPiece;
				bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
				bkType = cBoard.slot(from).cPiece.type;
				
				// di chuyen quan co de tao trang thai moi
				CBProcess.move(cBoard, from, to);
				
				// xet truong hop an duoc luon quan vua cua dich neu an duoc luon thi thoat ngay
				if(bkCPiece != null)
					if(bkCPiece.type == Const.KING){
						finalList.clear();
						finalList.add(from);
						finalList.add(to);
						
						// restore lai ban co
						CBProcess.move(cBoard, to, from);
						if(bkCPiece != null){
							cBoard.slot[to.x][to.y].cPiece = bkCPiece;
							cBoard.cArray[Const.WHITE].add(bkCPiece);
						}
						cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
						cBoard.slot(from).cPiece.type = bkType;
						break;
					}
				
				// tinh toan de quy
				val = maxSearch(1, cBoard, alpha, beta);
				
				// lam moi danh sach neu co gia tri tot hon
				if(val < beta){
					beta = val;
					finalList.clear();
					finalList.add(from);
					finalList.add(to);
				}
				// lay cac nuoc di co the dung lam trang thai moi
				// voi dieu kien nuoc di do khong dan den vua bi chet ( gia tri tra ve la MIN_VALUE o ham Minsearch)
				else if(val == beta && val != Integer.MAX_VALUE){
					finalList.add(from);
					finalList.add(to);
				}
				
				// restore lai ban co
				CBProcess.move(cBoard, to, from);
				if(bkCPiece != null){
					cBoard.slot[to.x][to.y].cPiece = bkCPiece;
					cBoard.cArray[Const.WHITE].add(bkCPiece);
				}
				cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
				cBoard.slot(from).cPiece.type = bkType;
			}
			
			// neu list di chuyen rong thi return ra ngoai, nghia la ben com da bi het nuoc di
			// dieu nay xay ra khi quan king la quan cuoi cung bi an
			if(finalList.size() == 0)
				return;
			
			// lay ngau nhien mot cap trong finalList
			int choose = random.nextInt(finalList.size() / 2) * 2;	// lay vi tri from
			it = finalList.iterator();
			for(int i = 0; i < choose; i++)
				it.next();
			
			from = it.next();
			to = it.next();
			
			// nuoc di duoc chon, dung de ve
			humanVSCom.coordEnemyMoved = to;
			// backup nuoc di
			
			humanVSCom.listUndo.push(cBoard.slot(from).cPiece.firstMoved);
			humanVSCom.listUndo.push(humanVSCom.isEnemySelect);
			humanVSCom.listUndo.push(cBoard.slot(to).cPiece);
			humanVSCom.listUndo.push(to);
			humanVSCom.listUndo.push(from);
			humanVSCom.listUndo.push(Const.NORMAL);
			// di chuyen
			///////////////////////////////////////////////////////////////////////////////////////////////
			// ve animation
			humanVSCom.panelChessBoard.animate(from, to, cBoard.slot(from).cPiece.type, cBoard.slot(from).cPiece.color);
			//////////////////////////////////////////////////////////////////////////////////////////////
			
			CBProcess.finalMove(cBoard, from, to, humanVSCom);
			// cap nhat vao list
			humanVSCom.ways[Const.BLACK].add(from + " ---> " + to);
			humanVSCom.lstWays[Const.BLACK].setListData(humanVSCom.ways[Const.BLACK]);
		}
	}
	
	public int maxSearch(int depth, CBoard cBoard, int alpha, int beta){
		// truong hop nut dang xet la nut la
		if(depth >= this.depth){
			// refresh lai cac nuoc di
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
			// toa do bat dau di chuyen
			from = it.next();
			// toa do toi
			to = it.next();
			
			// backup quan o vi tri se den va tbBit
			bkCPiece = cBoard.slot[to.x][to.y].cPiece;
			bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
			bkType = cBoard.slot(from).cPiece.type; // de phong truong hop phong hau
			
			// di chuyen quan co de tao trang thai ban co moi
			CBProcess.move(cBoard, from, to);
			
			// neu nuoc di dan den quan vua bi an thi tra ve gia tri nho nhat cua int
			if(depth == 1 && bkCPiece != null)
				if(bkCPiece.type == Const.KING ){
					// restore lai ban co
					CBProcess.move(cBoard, to, from);
					if(bkCPiece != null){
						cBoard.slot[to.x][to.y].cPiece = bkCPiece;
						cBoard.cArray[Const.BLACK].add(bkCPiece);
					}
					
					cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
					cBoard.slot(from).cPiece.type = bkType;
					
					return Integer.MAX_VALUE;
				}
			
			// tinh toan de quy
			max = Math.max(max, minSearch(depth + 1, cBoard, alpha, beta));
			
			// restore lai ban co
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
			// repfresh lai cac nuoc di
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
			// toa do bat dau di chuyen
			from = it.next();
			// toa do toi
			to = it.next();
			
			// backup quan o vi tri se den va tbBit
			bkCPiece = cBoard.slot[to.x][to.y].cPiece;
			bkFirstMoved = cBoard.slot(from).cPiece.firstMoved;
			bkType = cBoard.slot(from).cPiece.type; // de phong truong hop phong hau
			
			// di chuyen quan co
			CBProcess.move(cBoard, from, to);
			
			// neu nuoc di dan den quan vua bi an thi tra ve gia tri nho nhat cua int
			if(depth == 1 && bkCPiece != null)
				if(bkCPiece.type == Const.KING ){
					
					// restore lai ban co
					CBProcess.move(cBoard, to, from);
					if(bkCPiece != null){
						cBoard.slot[to.x][to.y].cPiece = bkCPiece;
						cBoard.cArray[Const.WHITE].add(bkCPiece);
					}
					
					cBoard.slot(from).cPiece.firstMoved = bkFirstMoved;
					cBoard.slot(from).cPiece.type = bkType;
					
					return Integer.MIN_VALUE;
				}
			
			// tinh toan de quy
			min = Math.min(min, maxSearch(depth + 1, cBoard, alpha, beta));
			
			// restore lai ban co
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
