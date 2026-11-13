package GameLogic;

import cards.Card;

import java.util.List;

public class Logic {

    public enum HandRank{
        HIGH_CARD,
        PAIR,
        FLUSH,
        STRAIGHT,
        THREE_OF_KIND,
        STRAIGHT_FLUSH
    }

    public static HandRank evalHand(List<Card> hand){
        if(hand.isEmpty() || hand.size() < 3){
            throw new IllegalArgumentException("Poker hand needs 3 cards");
        }

        boolean flush = isFlush(hand);
        boolean straight = isStraight(hand);
        boolean threeOfKind = isThreeOfKind(hand);
        boolean pair = isPair(hand);

        if(flush && straight){
            return HandRank.STRAIGHT_FLUSH;
        }

        if(threeOfKind){
            return HandRank.THREE_OF_KIND;
        }

        if(straight){
            return HandRank.STRAIGHT;
        }

        if(flush){
            return HandRank.FLUSH;
        }

        if(pair){
            return HandRank.PAIR;
        }

        return HandRank.HIGH_CARD;
    }

    public static int compareHands(List<Card> dealer, List<Card> player){
        HandRank dealerRank = evalHand(dealer);
        HandRank playerRank = evalHand(player);

        return Integer.compare(playerRank.ordinal(), dealerRank.ordinal());

    }

    public static int evalPPWinnings(List<Card> hand, int bet){
        HandRank playerRank = evalHand(hand);

        switch (playerRank) {
            case STRAIGHT_FLUSH -> {
                return 40;
            }
            case THREE_OF_KIND -> {
                return 30;
            }
            case STRAIGHT -> {
                return 6;
            }
            case FLUSH -> {
                return 3;
            }
            default -> {
                return 1;
            }
        }
    }


    private static boolean isThreeOfKind(List<Card> hand){
        return hand.get(0).getRank() == hand.get(1).getRank()
                && hand.get(1).getRank() == hand.get(2).getRank();
    }

    private static boolean isStraight(List<Card> hand){
        int[] ranks = hand.stream().mapToInt(Card::getValue).sorted().toArray();
        return ranks[0] + 1 == ranks[1] && ranks[1] + 1 == ranks[2];
    }

    private static boolean isFlush(List<Card> hand){
        return hand.get(0).getSuit() == hand.get(1).getSuit()
                && hand.get(1).getSuit() == hand.get(2).getSuit();
    }

    private static boolean isPair(List<Card> hand){
        return hand.get(0).getRank() == hand.get(1).getRank()
                || hand.get(0).getRank() == hand.get(2).getRank()
                || hand.get(1).getRank() == hand.get(2).getRank();
    }










}
