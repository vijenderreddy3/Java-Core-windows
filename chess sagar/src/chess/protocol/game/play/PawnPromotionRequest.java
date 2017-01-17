package chess.protocol.game.play;

import chess.model.IChessMan;

/**
 * 
 * An instance of this class represents a pawn move request with promotion.
 *
 */
public class PawnPromotionRequest extends ChessManMoveRequest
{
    private Class<? extends IChessMan> promotionType;

    public Class<? extends IChessMan> getPromotionType()
    {
        return promotionType;
    }

    public void setPromotionType(Class<? extends IChessMan> promotionType)
    {
        this.promotionType = promotionType;
    }
}
